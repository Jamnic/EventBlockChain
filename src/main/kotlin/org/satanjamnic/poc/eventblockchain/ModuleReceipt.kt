package org.satanjamnic.poc.eventblockchain

import org.satanjamnic.poc.eventblockchain.businessprocess.step.BusinessProcessStep

class ModuleReceipt(
        private val moduleName: String,
        private val businessProcessSteps: List<BusinessProcessStep>
) {

    fun name() : String {
        return moduleName
    }

    fun businessProcessSteps() : List<BusinessProcessStep> {
        return businessProcessSteps
    }
}