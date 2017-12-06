package org.satanjamnic.poc.eventblockchain.microservices.businessprocess.step

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.common.event.MissingEvent
import org.satanjamnic.poc.eventblockchain.common.event.type.EventType

class Process(
        private val businessProcess: BusinessProcess,
        private val consumedEventType: EventType,
        private val producedEventType: EventType
) : BusinessProcessStep {

    constructor(
            businessProcess: BusinessProcess,
            consumedEventType: String,
            producedEventType: String
    ) : this(businessProcess, EventType(consumedEventType), EventType(producedEventType))

    override fun businessAction(): BusinessProcess {
        return businessProcess
    }

    override fun producedEventType(): EventType {
        return producedEventType
    }

    override fun consumeEventsAndCreateNewEvent(vararg event: Event): Event {
        val consumedEvent = event.find { it.type() == consumedEventType }
        return if (consumedEvent != null)
            BaseEvent(producedEventType, consumedEvent.businessProcessId())
        else
            MissingEvent()
    }
}