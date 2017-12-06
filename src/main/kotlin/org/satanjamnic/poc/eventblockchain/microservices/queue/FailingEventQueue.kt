package org.satanjamnic.poc.eventblockchain.microservices.queue

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event

class FailingEventQueue(
        name: String,
        private val savedEvents: MutableList<Event> = mutableListOf(),
        private val eventQueue: EventQueue = ExistingEventQueue(name)
) : EventQueue by eventQueue {

    override fun publish(businessProcess: BusinessProcess, event: Event) {
        savedEvents.add(event)
        // Fail to notify listeners
    }

    override fun listEvents(): List<Event> {
        return savedEvents
    }
}