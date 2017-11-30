package org.satanjamnic.poc.eventblockchain.businessprocess.step

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessAction
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.MissingEvent
import org.satanjamnic.poc.eventblockchain.event.type.EventType

class Consume(
        private val businessAction: BusinessAction,
        private val consumedEvents: List<EventType>
) : BusinessProcessStep {

    constructor(
            businessAction: BusinessAction,
            vararg consumedEvents: String
    ) : this(businessAction, consumedEvents.map { EventType(it) })

    override fun businessAction(): BusinessAction {
        return businessAction
    }

    // TODO missing event Type or change implementation
    override fun producedEventType(): EventType {
        return EventType("")
    }

    override fun consumeEventsAndCreateNewEvent(vararg event: Event): Event {
        println("$businessAction - consumed event ${event.fold("", { acc: String, event -> "$$event, $acc" })}")
        return if (event.map { it.type() }.containsAll(consumedEvents))
            MissingEvent()
        else
            MissingEvent()
    }
}