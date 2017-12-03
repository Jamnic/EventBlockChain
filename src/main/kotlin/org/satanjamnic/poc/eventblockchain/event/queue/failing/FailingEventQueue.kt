package org.satanjamnic.poc.eventblockchain.event.queue.failing

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.queue.BaseEventQueue
import org.satanjamnic.poc.eventblockchain.event.queue.EventQueue

class FailingEventQueue(
        name: String,
        private val savedEvents: MutableList<Event> = mutableListOf(),
        private val eventQueue: EventQueue = BaseEventQueue(name)
) : EventQueue by eventQueue {

    override fun publish(businessProcess: BusinessProcess, event: Event) {
        savedEvents.add(event)
        // Fail to notify listeners
    }

    override fun listEvents(): List<Event> {
        return savedEvents
    }
}