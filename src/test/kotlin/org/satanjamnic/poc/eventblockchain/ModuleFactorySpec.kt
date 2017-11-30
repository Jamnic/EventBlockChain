package org.satanjamnic.poc.eventblockchain

import org.junit.Test
import org.satanjamnic.poc.eventblockchain.businessprocess.BaseBusinessAction
import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessAction
import org.satanjamnic.poc.eventblockchain.businessprocess.step.Create
import org.satanjamnic.poc.eventblockchain.module.Module
import org.satanjamnic.poc.eventblockchain.module.factory.BaseModuleFactory

class ModuleFactorySpec {

    @Test
    fun createModule() {
        // given
        val moduleFactory = BaseModuleFactory()
        val moduleReceipt = ModuleReceipt("PolicyManagement", listOf())

        // when
        val policyManagementModule: Module = moduleFactory.create(moduleReceipt)

        // then
        assert(policyManagementModule.name() == "PolicyManagement")
    }

    @Test
    fun defineBusinessSteps() {
        // given
        val businessAction = BaseBusinessAction("any action")
        val moduleFactory = BaseModuleFactory()
        val moduleReceipt = ModuleReceipt("PolicyManagement", listOf(Create(businessAction, "PolicyCreated")))

        // when
        val policyManagementModule: Module = moduleFactory.create(moduleReceipt)

        // then
        assert(policyManagementModule.businessSteps().size == 1)
    }
}