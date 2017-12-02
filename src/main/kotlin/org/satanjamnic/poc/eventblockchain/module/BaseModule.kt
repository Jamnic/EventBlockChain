package org.satanjamnic.poc.eventblockchain.module

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.businessprocess.step.BusinessProcessStep
import org.satanjamnic.poc.eventblockchain.event.Event

class BaseModule(
        private val name: String,
        private val businessProcessSteps: List<BusinessProcessStep>,
        private val publisher: Publisher = BasePublisher()
) : Module, Publisher by publisher {

    override fun name(): String {
        return name
    }

    override fun process(businessProcess: BusinessProcess, vararg event: Event) {
        val businessProcessStep = businessProcessSteps
                .find { businessProcess == it.businessAction() }

        if (businessProcessStep != null) {
            eventQueueByProcessStep(businessProcessStep)
                    .publish(businessProcess, produceEvent(businessProcessStep, *event))
        }
    }

    override fun notify(businessProcess: BusinessProcess, event: Event) {
        process(businessProcess, event)
    }

    override fun toString(): String {
        return "$name Module"
    }

    private fun produceEvent(businessProcessStep: BusinessProcessStep, vararg events: Event): Event {
        return businessProcessStep.consumeEventsAndCreateNewEvent(*events)
    }
}