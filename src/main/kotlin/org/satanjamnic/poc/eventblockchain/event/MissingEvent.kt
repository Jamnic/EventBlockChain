package org.satanjamnic.poc.eventblockchain.event

import org.satanjamnic.poc.eventblockchain.event.type.EventType

class MissingEvent : Event {
    override fun type(): EventType {
        return EventType("Missing Event")
    }
}