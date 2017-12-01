package org.satanjamnic.poc.eventblockchain

import org.junit.Test
import org.satanjamnic.poc.eventblockchain.businessprocess.BaseBusinessAction
import org.satanjamnic.poc.eventblockchain.businessprocess.step.Create
import org.satanjamnic.poc.eventblockchain.businessprocess.step.Process
import org.satanjamnic.poc.eventblockchain.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.event.queue.EventQueue
import org.satanjamnic.poc.eventblockchain.event.type.EventType
import org.satanjamnic.poc.eventblockchain.module.BaseModule

class ModuleSpec {

    @Test
    fun createSimpleBusinessProcessEvent() {
        // given
        val businessAction = BaseBusinessAction("create policy")
        val policyManagement = BaseModule("PolicyManagement", listOf(Create(businessAction, "PolicyCreated")))

        val policyCreatedQueue = EventQueue("PolicyCreated")
        policyManagement.publishesTo(policyCreatedQueue)

        // when
        policyManagement.process(businessAction)

        // then
        assert(policyCreatedQueue.listEvents().find { it.type() == EventType("PolicyCreated") } != null)
    }

    @Test
    fun createChainedBusinessProcessEvents() {
        // given
        val businessAction = BaseBusinessAction("create policy")
        val policyManagement = BaseModule("PolicyManagement", listOf(Create(businessAction, "PolicyCreated")))
        val billing = BaseModule("Billing", listOf(Process(businessAction, "PolicyCreated", "AccountCreated")))

        val policyCreatedQueue = EventQueue("PolicyCreated")
        policyManagement.publishesTo(policyCreatedQueue)
        policyCreatedQueue.registerListener(billing)
        val accountCreatedQueue = EventQueue("AccountCreated")
        billing.publishesTo(accountCreatedQueue)

        // when
        policyManagement.process(businessAction)

        // then
        assert(accountCreatedQueue.listEvents().find { it.type() == EventType("AccountCreated") } != null)
    }

    @Test
    fun twoConsumersConsumeBusinessProcessEvent() {
        // given
        val createPolicyBusinessProcess = BaseBusinessAction("create policy")

        val policyManagement = BaseModule("PolicyManagement", listOf(Create(createPolicyBusinessProcess, "PolicyCreated")))
        val billing = BaseModule("Billing", listOf(Process(createPolicyBusinessProcess, "PolicyCreated", "AccountCreated")))
        val registries = BaseModule("Registries", listOf(Process(createPolicyBusinessProcess, "PolicyCreated", "RegistryCreated")))

        val policyCreatedQueue = EventQueue("PolicyCreated")
        policyCreatedQueue.registerListener(billing)
        policyCreatedQueue.registerListener(registries)
        policyManagement.publishesTo(policyCreatedQueue)

        val policyAccountCreatedQueue = EventQueue("AccountCreated")
        billing.publishesTo(policyAccountCreatedQueue)

        val registryCreatedQueue = EventQueue("RegistryCreated")
        registries.publishesTo(registryCreatedQueue)

        // when
        policyManagement.process(createPolicyBusinessProcess)

        // then
        assert(policyCreatedQueue.listEvents().find { it.type() == EventType("PolicyCreated") } != null)
        assert(policyAccountCreatedQueue.listEvents().find { it.type() == EventType("AccountCreated") } != null)
        assert(registryCreatedQueue.listEvents().find { it.type() == EventType("RegistryCreated") } != null)
    }

//    @Test
//    fun shouldNotMixTwoPolicyCreationEvents() {
//        // given
//        val businessAction = BaseBusinessAction("create policy")
//
//        val policyManagement = BaseModule("PolicyManagement", listOf(Create(businessAction, "PolicyCreated")))
//        val billing = BaseModule("Billing", listOf(Process(businessAction, "PolicyCreated", "AccountCreated")))
//
//        val policyCreatedQueue = EventQueue("PolicyCreated")
//        policyCreatedQueue.registerListener(billing)
//        policyManagement.publishesTo(policyCreatedQueue)
//
//        val policyAccountCreatedQueue = EventQueue("AccountCreated")
//        billing.publishesTo(policyAccountCreatedQueue)
//
//        // when
//        policyManagement.process(businessAction)
//        policyManagement.process(businessAction)
//
//        // then
//        assert(policyCreatedQueue.listEvents().find { it == BaseEvent("PolicyCreated", 1) } != null)
//        assert(policyCreatedQueue.listEvents().find {it == BaseEvent("PolicyCreated", 2)} != null)
//        assert(policyCreatedQueue.listEvents().find {it == BaseEvent("AccountCreated", 1) && it.createdFrom(BaseEvent("PolicyCreated",1))} != null)
//        assert(policyCreatedQueue.listEvents().find {it == BaseEvent("AccountCreated", 2) && it.createdFrom(BaseEvent("PolicyCreated",1)} != null)
//    }
}