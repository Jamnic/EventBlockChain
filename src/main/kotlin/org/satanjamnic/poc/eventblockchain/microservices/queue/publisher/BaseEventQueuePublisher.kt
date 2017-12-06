package org.satanjamnic.poc.eventblockchain.microservices.queue.publisher

import org.satanjamnic.poc.eventblockchain.microservices.businessprocess.step.BusinessProcessStep
import org.satanjamnic.poc.eventblockchain.microservices.queue.EventQueue
import org.satanjamnic.poc.eventblockchain.microservices.queue.MissingEventQueue

class BaseEventQueuePublisher(
        private val publishesTo: MutableList<EventQueue> = mutableListOf()
) : EventQueuePublisher {

    override fun publishesTo(eventQueue: EventQueue) {
        publishesTo.add(eventQueue)
    }

    override fun findQueueToPublish(businessProcessStep: BusinessProcessStep): EventQueue {
        return publishesTo
                .find { it.publishedEventType() == businessProcessStep.producedEventType() }
                ?: MissingEventQueue()
    }
}