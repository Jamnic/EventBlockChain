package org.satanjamnic.poc.eventblockchain.businessprocess

import org.satanjamnic.poc.eventblockchain.event.type.EventType

interface BusinessProcess {
    fun name(): String
    fun expectedResult(): List<EventType>
}