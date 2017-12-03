package org.satanjamnic.poc.eventblockchain.event.queue

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.type.EventType
import org.satanjamnic.poc.eventblockchain.module.listener.Listener

class BaseEventQueue(
        private val eventType: EventType,
        private val listeners: MutableList<Listener> = mutableListOf(),
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

    override fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    override fun toString(): String {
        return "$eventType Queue"
    }
}