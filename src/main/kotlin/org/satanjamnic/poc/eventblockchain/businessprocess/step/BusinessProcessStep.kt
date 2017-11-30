package org.satanjamnic.poc.eventblockchain.businessprocess.step

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessAction
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.type.EventType

interface BusinessProcessStep {
    fun producedEventType(): EventType
    fun consumeEventsAndCreateNewEvent(vararg event: Event): Event
    fun businessAction() : BusinessAction
}