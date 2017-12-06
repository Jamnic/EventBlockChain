package org.satanjamnic.poc.eventblockchain.microservices.module

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.microservices.queue.listener.EventQueueListener
import org.satanjamnic.poc.eventblockchain.microservices.queue.publisher.EventQueuePublisher

interface Module : EventQueueListener, EventQueuePublisher {
    fun name(): String
    fun process(businessProcess: BusinessProcess, vararg event: Event)
    fun listEvents(tableName: String): List<Event>
}