package org.satanjamnic.poc.eventblockchain.event

import org.satanjamnic.poc.eventblockchain.event.type.EventType

class MissingEvent : Event {
    override fun businessProcessId(): Long = throw IllegalStateException("There is no such Event")
    override fun type(): EventType {
        return EventType("Missing Event")
    }
}