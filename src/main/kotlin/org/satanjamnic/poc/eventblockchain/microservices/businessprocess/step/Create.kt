package org.satanjamnic.poc.eventblockchain.microservices.businessprocess.step

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.common.event.type.EventType
import org.satanjamnic.poc.eventblockchain.microservices.businessprocess.factory.BusinessProcessIdFactory

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