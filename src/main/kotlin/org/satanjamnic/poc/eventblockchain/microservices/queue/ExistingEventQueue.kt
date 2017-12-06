package org.satanjamnic.poc.eventblockchain.microservices.queue

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.common.event.type.EventType
import org.satanjamnic.poc.eventblockchain.microservices.queue.listener.EventQueueListener

class ExistingEventQueue(
        private val eventType: EventType,
        private val listeners: MutableList<EventQueueListener> = mutableListOf(),
        private val savedEvents: MutableList<Event> = mutableListOf()
) : EventQueue {

    constructor(
            eventTypeName: String
    ) : this(EventType(eventTypeName))

    override fun publishedEventType(): EventType {
        return eventType
    }

    override fun publish(businessProcess: BusinessProcess, event: Event) {
        savedEvents.add(event)
        listeners.forEach { it.notify(businessProcess, event) }
    }

    override fun listEvents(): List<Event> {
        return savedEvents
    }

    override fun registerListener(listener: EventQueueListener) {
        listeners.add(listener)
    }

    override fun toString(): String {
        return "$eventType Queue"
    }
}