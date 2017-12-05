package org.satanjamnic.poc.eventblockchain.event.pool.consumer

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.pool.EventPool
import org.satanjamnic.poc.eventblockchain.event.pool.Observer

class EventPoolConsumer(
        private val eventPool: EventPool,
        vararg private val businessProcess: BusinessProcess
) : Observer {

    override fun beNotified() {
        if (eventPool.isNotEmpty()) {
            eventPool
                    .map { it.businessProcessId() }
                    .toSet()
                    .forEach { id ->
                        val group = findEventsByProcessId(id)
                        val process = findProcessMatchingGroup(group)

                        if (process != null) {
                            eventPool.removeAll(group)
                        }
                    }
        }
    }

    private fun findEventsByProcessId(id: Long): List<Event> {
        return eventPool
                .filter { it.businessProcessId() == id }
    }

    private fun findProcessMatchingGroup(group: List<Event>): BusinessProcess? {
        return businessProcess
                .find { it.validate(group.map { it.type() }) }
    }
}