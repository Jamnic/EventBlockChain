package org.satanjamnic.poc.eventblockchain.microservices.businessprocess.step

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.common.event.type.EventType

// TODO rename whole businessProcessStep section
interface BusinessProcessStep {
    fun producedEventType(): EventType
    fun consumeEventsAndCreateNewEvent(vararg event: Event): Event
    fun businessAction(): BusinessProcess
}