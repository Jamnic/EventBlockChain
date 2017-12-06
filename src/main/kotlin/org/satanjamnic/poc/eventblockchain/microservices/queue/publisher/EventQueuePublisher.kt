package org.satanjamnic.poc.eventblockchain.microservices.queue.publisher

import org.satanjamnic.poc.eventblockchain.microservices.businessprocess.step.BusinessProcessStep
import org.satanjamnic.poc.eventblockchain.microservices.queue.EventQueue

interface EventQueuePublisher {
    // TODO consider using a EventType/EventQueue map
    fun publishesTo(eventQueue: EventQueue)

    fun findQueueToPublish(businessProcessStep: BusinessProcessStep): EventQueue
}