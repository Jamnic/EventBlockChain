package org.satanjamnic.poc.eventblockchain.microservices.module

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.microservices.businessprocess.step.BusinessProcessStep

class FailingModule(
        name: String,
        vararg businessProcessSteps: BusinessProcessStep
) : ExistingModule(name, *businessProcessSteps) {

    override fun process(businessProcess: BusinessProcess, vararg event: Event) {
        // Fail to process events
    }
}