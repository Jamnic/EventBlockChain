package org.satanjamnic.poc.eventblockchain

import org.junit.Test
import org.satanjamnic.poc.eventblockchain.businessprocess.BaseBusinessAction
import org.satanjamnic.poc.eventblockchain.businessprocess.step.Consume
import org.satanjamnic.poc.eventblockchain.businessprocess.step.Create
import org.satanjamnic.poc.eventblockchain.businessprocess.step.Process
import org.satanjamnic.poc.eventblockchain.event.queue.EventQueue
import org.satanjamnic.poc.eventblockchain.module.BaseModule

class ModuleSpec {

//    @Test
//    fun createSimpleBusinessProcessEvent() {
//        // given
//        val policyManagement = BaseModule("PolicyManagement", listOf(Create("PolicyCreated")))
//
//        val policyCreationBusinessProcess = BaseBusinessAction("PolicyCreated")
//
//        // when
//        val policyCreatedEvent: Event = policyManagement.process(policyCreationBusinessProcess)
//
//        // then
//        assert(policyCreatedEvent.type() == "PolicyCreated")
//    }
//
//    @Test
//    fun createChainedBusinessProcessEvents() {
//        // given
//        val policyManagement = BaseModule("PolicyManagement", listOf(Create("PolicyCreated")))
//        val billing = BaseModule("Billing", listOf(Process("PolicyCreated", "PolicyAccountCreated")))
//
//        val policyAccountCreationBusinessProcess = BaseBusinessAction("PolicyCreated", "PolicyAccountCreated")
//
//        // when
//        val policyCreatedEvent: Event = policyManagement.process(policyAccountCreationBusinessProcess)
//        val policyAccountCreatedEvent: Event = billing.process(policyAccountCreationBusinessProcess, policyCreatedEvent)
//
//        // then
//        assert(policyAccountCreatedEvent.type() == EventType("PolicyAccountCreated"))
//    }

    @Test
    fun twoConsumersConsumeBusinessProcessEvent() {
        // given
        val createPolicyBusinessProcess = BaseBusinessAction("create policy")

        val policyManagement = BaseModule("PolicyManagement", listOf(Create(createPolicyBusinessProcess, "PolicyCreated")))
        val billing = BaseModule("Billing", listOf(Process(createPolicyBusinessProcess, "PolicyCreated", "PolicyAccountCreated")))
        val registries = BaseModule("Registries", listOf(Process(createPolicyBusinessProcess, "PolicyCreated", "RegistryCreated")))
        val miner = BaseModule("Miner", listOf(Consume(createPolicyBusinessProcess, "PolicyCreated", "PolicyAccountCreated", "RegistryCreated")))


        val policyCreatedQueue = EventQueue("PolicyCreated")
        policyCreatedQueue.registerListener(billing)
        policyCreatedQueue.registerListener(registries)
        policyManagement.publishesTo(policyCreatedQueue)

        val policyAccountCreatedQueue = EventQueue("PolicyAccountCreated")
        billing.publishesTo(policyCreatedQueue)
        policyAccountCreatedQueue.registerListener(miner)

        val registryCreatedQueue = EventQueue("RegistryCreated")
        registries.publishesTo(registryCreatedQueue)
        registryCreatedQueue.registerListener(miner)

        // when
        policyManagement.process(createPolicyBusinessProcess)

        // then
        println(miner)
    }
}