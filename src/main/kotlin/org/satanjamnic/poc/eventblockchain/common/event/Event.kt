package org.satanjamnic.poc.eventblockchain.common.event

import org.satanjamnic.poc.eventblockchain.common.event.type.EventType

interface Event {
    fun type(): EventType
    fun businessProcessId(): Long
}