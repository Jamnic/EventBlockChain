package org.satanjamnic.poc.eventblockchain.businessprocess.step

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessAction
import org.satanjamnic.poc.eventblockchain.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.type.EventType

class Create(
        private val businessAction: BusinessAction,
        private val producedEventType: EventType
) : BusinessProcessStep {

    constructor(
            businessAction: BusinessAction,
            producedEventTypeName: String
    ) : this(businessAction, EventType(producedEventTypeName))

    override fun businessAction(): BusinessAction {
        return businessAction
    }

    override fun producedEventType(): EventType {
        return producedEventType
    }

    override fun consumeEventsAndCreateNewEvent(vararg event: Event): Event {
        println("$businessAction - created event $producedEventType")
        return BaseEvent(producedEventType)
    }
}