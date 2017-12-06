package org.satanjamnic.poc.eventblockchain.microservices.queue

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.common.event.type.EventType
import org.satanjamnic.poc.eventblockchain.microservices.queue.listener.EventQueueListener

interface EventQueue {
    fun publishedEventType(): EventType
    fun publish(businessProcess: BusinessProcess, event: Event)
    fun listEvents(): List<Event>

    // TODO move to other interface
    fun registerListener(listener: EventQueueListener)
}