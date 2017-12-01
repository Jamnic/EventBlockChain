package org.satanjamnic.poc.eventblockchain.event.queue

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessAction
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.type.EventType
import org.satanjamnic.poc.eventblockchain.module.Module

class EventQueue(
        private val eventType: EventType,
        private val listeners: MutableList<Module> = mutableListOf(),
        private val savedEvents: MutableList<Event> = mutableListOf()
) {
    constructor(eventTypeName: String) : this(EventType(eventTypeName))

    fun registerListener(listener: Module) {
        listeners.add(listener)
    }

    fun publishedEventType(): EventType {
        return eventType
    }

    fun publish(businessAction: BusinessAction, event: Event) {
        savedEvents.add(event)
        listeners.forEach { it.notify(businessAction, event) }
    }

    override fun toString(): String {
        return "$eventType Queue"
    }

    fun listEvents() : List<Event> {
        return savedEvents
    }
}