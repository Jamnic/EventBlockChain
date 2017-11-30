package org.satanjamnic.poc.eventblockchain.event

import org.satanjamnic.poc.eventblockchain.event.type.EventType

class BaseEvent(
        private val type: EventType
) : Event {

    override fun type(): EventType {
        return type
    }

    override fun toString(): String {
        return type.toString()
    }
}