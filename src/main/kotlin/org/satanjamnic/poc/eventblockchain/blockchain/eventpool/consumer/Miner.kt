package org.satanjamnic.poc.eventblockchain.blockchain.eventpool.consumer

import org.satanjamnic.poc.eventblockchain.blockchain.eventpool.EventPool
import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event
import kotlin.concurrent.thread

class Miner(
        private val eventPool: EventPool,
        vararg private val businessProcess: BusinessProcess
) : EventPoolConsumer {

    var blocked = false
    var consumed = 0

    override fun consumed(): Int {
        return consumed
    }

    override fun isBlocked(): Boolean {
        return blocked
    }

    override fun beNotified() {
        if (eventPool.isNotEmpty() && !blocked) {
            blocked = true
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
            blocked = false
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