package org.satanjamnic.poc.eventblockchain.module.factory

import org.satanjamnic.poc.eventblockchain.module.Module
import org.satanjamnic.poc.eventblockchain.ModuleReceipt
import org.satanjamnic.poc.eventblockchain.module.BaseModule

class BaseModuleFactory : ModuleFactory {
    override fun create(moduleReceipt: ModuleReceipt): Module {
        return BaseModule(moduleReceipt.name(), moduleReceipt.businessProcessSteps())
    }
}