package org.satanjamnic.poc.eventblockchain.module.publisher

import org.satanjamnic.poc.eventblockchain.businessprocess.step.BusinessProcessStep
import org.satanjamnic.poc.eventblockchain.event.queue.EventQueue

interface Publisher {
    // TODO consider using a EventType/EventQueue map
    fun publishesTo(eventQueue: EventQueue)

    fun findQueueToPublish(businessProcessStep: BusinessProcessStep): EventQueue
}