package org.satanjamnic.poc.eventblockchain.common.businessprocess

import org.satanjamnic.poc.eventblockchain.common.event.type.EventType

interface BusinessProcess {
    fun name(): String
    fun validate(eventGroup: List<EventType>): Boolean
}