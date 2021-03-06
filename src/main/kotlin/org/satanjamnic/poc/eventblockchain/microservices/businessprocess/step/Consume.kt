package org.satanjamnic.poc.eventblockchain.microservices.businessprocess.step

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.common.event.MissingEvent
import org.satanjamnic.poc.eventblockchain.common.event.type.EventType

class Consume(
        private val businessProcess: BusinessProcess,
        private val consumedEvents: List<EventType>
) : BusinessProcessStep {

    constructor(
            businessProcess: BusinessProcess,
            vararg consumedEvents: String
    ) : this(businessProcess, consumedEvents.map { EventType(it) })

    override fun businessAction(): BusinessProcess {
        return businessProcess
    }

    // TODO missing event Type or change implementation
    override fun producedEventType(): EventType {
        return EventType("")
    }

    override fun consumeEventsAndCreateNewEvent(vararg event: Event): Event {
        return if (event.map { it.type() }.containsAll(consumedEvents))
            MissingEvent()
        else
            MissingEvent()
    }
}