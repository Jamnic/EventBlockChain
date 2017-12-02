package org.satanjamnic.poc.eventblockchain.event.queue

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.type.EventType
import org.satanjamnic.poc.eventblockchain.module.Listener

class MissingEventQueue : EventQueue {
    override fun publishedEventType(): EventType = throw IllegalStateException("There is no such EventQueue!")
    override fun listEvents(): List<Event> = throw IllegalStateException("There is no such EventQueue!")
    override fun registerListener(listener: Listener) = throw IllegalStateException("There is no such EventQueue!")

    override fun publish(businessProcess: BusinessProcess, event: Event) {
    }

    override fun toString(): String {
        return "Missing Queue"
    }
}