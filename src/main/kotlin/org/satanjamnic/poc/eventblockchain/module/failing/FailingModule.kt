package org.satanjamnic.poc.eventblockchain.module.failing

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.businessprocess.step.BusinessProcessStep
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.module.BaseModule

class FailingModule(
        name: String,
        businessProcessSteps: List<BusinessProcessStep>
) : BaseModule(name, businessProcessSteps) {

    override fun process(businessProcess: BusinessProcess, vararg event: Event) {
        // Fail to process events
    }
}