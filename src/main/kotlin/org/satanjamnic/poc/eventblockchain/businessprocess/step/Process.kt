package org.satanjamnic.poc.eventblockchain.businessprocess.step

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessAction
import org.satanjamnic.poc.eventblockchain.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.MissingEvent
import org.satanjamnic.poc.eventblockchain.event.type.EventType

class Process(
        private val businessAction: BusinessAction,
        private val consumedEventType: EventType,
        private val producedEventType: EventType
) : BusinessProcessStep {

    constructor(
            businessAction: BusinessAction,
            consumedEventType: String,
            producedEventType: String
    ) : this(businessAction, EventType(consumedEventType), EventType(producedEventType))

    override fun businessAction(): BusinessAction {
        return businessAction
    }

    override fun producedEventType(): EventType {
        return producedEventType
    }

    override fun consumeEventsAndCreateNewEvent(vararg event: Event): Event {
        println("$businessAction - processed event ${event.fold("", { acc: String, event -> "$event, $acc" })}")
        return if (event.map { it.type() }.contains(consumedEventType))
            BaseEvent(producedEventType)
        else
            MissingEvent()
    }
}