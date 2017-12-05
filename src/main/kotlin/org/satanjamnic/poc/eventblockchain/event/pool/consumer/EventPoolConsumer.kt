package org.satanjamnic.poc.eventblockchain.event.pool.consumer

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.pool.EventPool
import org.satanjamnic.poc.eventblockchain.event.pool.Observer
import kotlin.concurrent.thread

class EventPoolConsumer(
        private val eventPool: EventPool,
        vararg private val businessProcess: BusinessProcess
) : Observer {

    var isBlocked = false
    var consumed = 0

    override fun beNotified() {
        if (eventPool.isNotEmpty() && !isBlocked) {
            isBlocked = true
            thread {
                val copiedList = eventPool.list()

                copiedList.map { it.businessProcessId() }
                        .toSet()
                        .forEach { id ->
                            val group = findEventsByProcessId(copiedList, id)
                            val process = findProcessMatchingGroup(group)

                            if (process != null) {
                                eventPool.removeAll(group)
                                consumed += group.size
                            }
                        }
            }
            isBlocked = false
        }
    }

    private fun findEventsByProcessId(copiedEvents: List<Event>, id: Long): List<Event> {
        return copiedEvents
                .filter { it.businessProcessId() == id }
    }

    private fun findProcessMatchingGroup(group: List<Event>): BusinessProcess? {
        return businessProcess
                .find { it.validate(group.map { it.type() }) }
    }
}