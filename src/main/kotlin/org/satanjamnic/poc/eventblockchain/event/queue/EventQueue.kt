package org.satanjamnic.poc.eventblockchain.event.queue

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.type.EventType
import org.satanjamnic.poc.eventblockchain.module.listener.Listener

interface EventQueue {
    fun publishedEventType(): EventType
    fun publish(businessProcess: BusinessProcess, event: Event)
    fun listEvents(): List<Event>
    fun registerListener(listener: Listener)
}