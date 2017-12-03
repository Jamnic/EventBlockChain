package org.satanjamnic.poc.eventblockchain.event.pool

import org.satanjamnic.poc.eventblockchain.event.Event

class BaseEventPool(
        private val events: MutableList<Event> = mutableListOf()
) : EventPool {

    override operator fun plusAssign(event: Event) {
        events += event
    }

    override fun list() : MutableList<Event> {
        return events
    }
}