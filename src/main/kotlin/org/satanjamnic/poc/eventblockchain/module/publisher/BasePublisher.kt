package org.satanjamnic.poc.eventblockchain.module.publisher

import org.satanjamnic.poc.eventblockchain.businessprocess.step.BusinessProcessStep
import org.satanjamnic.poc.eventblockchain.event.queue.EventQueue
import org.satanjamnic.poc.eventblockchain.event.queue.MissingEventQueue

class BasePublisher(
        private val publishesTo: MutableList<EventQueue> = mutableListOf()
) : Publisher {

    override fun publishesTo(eventQueue: EventQueue) {
        publishesTo.add(eventQueue)
    }

    override fun findQueueToPublish(businessProcessStep: BusinessProcessStep): EventQueue {
        return publishesTo
                .find { it.publishedEventType() == businessProcessStep.producedEventType() }
                ?: MissingEventQueue()
    }
}