package org.satanjamnic.poc.eventblockchain.microservices.queue

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.common.event.type.EventType
import org.satanjamnic.poc.eventblockchain.microservices.queue.listener.EventQueueListener

class MissingEventQueue : EventQueue {
    override fun publishedEventType(): EventType = throw IllegalStateException("There is no such EventQueue!")
    override fun listEvents(): List<Event> = throw IllegalStateException("There is no such EventQueue!")
    override fun registerListener(listener: EventQueueListener) = throw IllegalStateException("There is no such EventQueue!")

    override fun publish(businessProcess: BusinessProcess, event: Event) {
    }

    override fun toString(): String {
        return "Missing Queue"
    }
}