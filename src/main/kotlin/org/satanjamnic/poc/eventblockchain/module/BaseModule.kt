package org.satanjamnic.poc.eventblockchain.module

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessAction
import org.satanjamnic.poc.eventblockchain.businessprocess.step.BusinessProcessStep
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.queue.EventQueue

class BaseModule(
        private val name: String,
        private val businessProcessSteps: List<BusinessProcessStep>,
        private val publishesTo: MutableList<EventQueue> = mutableListOf()
) : Module {
    override fun name(): String {
        return name
    }

    override fun businessSteps(): List<BusinessProcessStep> {
        return businessProcessSteps
    }

    override fun process(businessAction: BusinessAction, vararg event: Event) {
        val businessProcessStep = businessProcessSteps
                .find { businessAction == it.businessAction() }

        if (businessProcessStep != null) {

            val producedEvent = businessProcessStep.consumeEventsAndCreateNewEvent(*event)

            val eventQueue = publishesTo.find { it.publishedEventType() == businessProcessStep.producedEventType() }
            if (eventQueue != null) {
                eventQueue.publish(businessAction, producedEvent)
            }
        }
    }

    override fun publishesTo(eventQueue: EventQueue) {
        publishesTo.add(eventQueue)
    }

    override fun notify(businessAction: BusinessAction, event: Event) {
        process(businessAction, event)
    }

    override fun toString(): String {
        return "$name Module"
    }
}
