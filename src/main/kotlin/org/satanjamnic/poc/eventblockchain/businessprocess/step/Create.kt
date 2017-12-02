package org.satanjamnic.poc.eventblockchain.businessprocess.step

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.businessprocess.factory.BusinessProcessIdFactory
import org.satanjamnic.poc.eventblockchain.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.type.EventType

class Create(
        private val businessProcess: BusinessProcess,
        private val producedEventType: EventType,
        private val businessProcessIdFactory: BusinessProcessIdFactory
) : BusinessProcessStep {

    constructor(
            businessProcess: BusinessProcess,
            producedEventTypeName: String,
            businessProcessIdFactory: BusinessProcessIdFactory
    ) : this(businessProcess, EventType(producedEventTypeName), businessProcessIdFactory)

    override fun businessAction(): BusinessProcess {
        return businessProcess
    }

    override fun producedEventType(): EventType {
        return producedEventType
    }

    override fun consumeEventsAndCreateNewEvent(vararg event: Event): Event {
        return BaseEvent(producedEventType, businessProcessIdFactory.next())
    }
}