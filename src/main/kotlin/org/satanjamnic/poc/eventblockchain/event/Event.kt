package org.satanjamnic.poc.eventblockchain.event

import org.satanjamnic.poc.eventblockchain.event.type.EventType

interface Event {
    fun type() : EventType
}