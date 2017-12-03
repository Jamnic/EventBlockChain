package org.satanjamnic.poc.eventblockchain

import org.junit.Test
import org.satanjamnic.poc.eventblockchain.businessprocess.BaseBusinessProcess
import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.businessprocess.factory.BusinessProcessIdFactory
import org.satanjamnic.poc.eventblockchain.businessprocess.step.Create
import org.satanjamnic.poc.eventblockchain.businessprocess.step.Process
import org.satanjamnic.poc.eventblockchain.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.queue.BaseEventQueue
import org.satanjamnic.poc.eventblockchain.event.queue.EventQueue
import org.satanjamnic.poc.eventblockchain.module.BaseModule
import org.satanjamnic.poc.eventblockchain.module.Module

class ModuleSpec {

    @Test
    fun shouldCreateEvent() {
        // given
        val createPolicyBusinessProcess: BusinessProcess = BaseBusinessProcess("create policy")

        val policyManagement: Module = createPolicyManagementModule(createPolicyBusinessProcess)

        val policyCreatedQueue: EventQueue = BaseEventQueue("PolicyCreated")
        policyManagement.publishesTo(policyCreatedQueue)

        // when
        policyManagement.process(createPolicyBusinessProcess)

        // then
        assert(policyCreatedQueue.listEvents().find { it == BaseEvent("PolicyCreated", 1) } != null)
    }

    @Test
    fun shouldProcessCreatedEvent() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("create policy")

        val policyManagement: Module = createPolicyManagementModule(process)

        val billing: Module = createBillingModule(process)

        val policyCreatedQueue: EventQueue = BaseEventQueue("PolicyCreated")
        policyManagement.publishesTo(policyCreatedQueue)
        policyCreatedQueue.registerListener(billing)

        val accountCreatedQueue: EventQueue = BaseEventQueue("AccountCreated")
        billing.publishesTo(accountCreatedQueue)

        // when
        policyManagement.process(process)

        // then
        assertQueueContains(accountCreatedQueue, BaseEvent("AccountCreated", 1))
    }


    @Test
    fun shouldPublishToMultipleModules() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("create policy")

        val policyManagementModule: Module = createPolicyManagementModule(process)
        val billingModule: Module = createBillingModule(process)
        val registriesModule: Module = createRegistriesModule(process)

        val policyCreatedQueue: EventQueue = BaseEventQueue("PolicyCreated")
        policyCreatedQueue.registerListener(billingModule)
        policyCreatedQueue.registerListener(registriesModule)
        policyManagementModule.publishesTo(policyCreatedQueue)

        val policyAccountCreatedQueue: EventQueue = BaseEventQueue("AccountCreated")
        billingModule.publishesTo(policyAccountCreatedQueue)

        val registryCreatedQueue: EventQueue = BaseEventQueue("RegistryCreated")
        registriesModule.publishesTo(registryCreatedQueue)

        // when
        policyManagementModule.process(process)

        // then
        assert(policyCreatedQueue.listEvents().find { it == BaseEvent("PolicyCreated", 1) } != null)
        assert(policyAccountCreatedQueue.listEvents().find { it == BaseEvent("AccountCreated", 1) } != null)
        assert(registryCreatedQueue.listEvents().find { it == BaseEvent("RegistryCreated", 1) } != null)
    }

    @Test
    fun shouldNotMixBusinessProcesses() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("create policy")

        val policyManagementModule: Module = createPolicyManagementModule(process)

        val billingModule: Module = createBillingModule(process)

        val policyCreatedQueue: EventQueue = BaseEventQueue("PolicyCreated")
        policyCreatedQueue.registerListener(billingModule)
        policyManagementModule.publishesTo(policyCreatedQueue)

        val policyAccountCreatedQueue: EventQueue = BaseEventQueue("AccountCreated")
        billingModule.publishesTo(policyAccountCreatedQueue)

        // when
        policyManagementModule.process(process)
        policyManagementModule.process(process)

        // then
        assertQueueContains(policyCreatedQueue, BaseEvent("PolicyCreated", 1))
        assertQueueContains(policyCreatedQueue, BaseEvent("PolicyCreated", 2))
        assertQueueContains(policyAccountCreatedQueue, BaseEvent("AccountCreated", 1))
        assertQueueContains(policyAccountCreatedQueue, BaseEvent("AccountCreated", 2))
    }

    private fun createRegistriesModule(process: BusinessProcess): BaseModule {
        return BaseModule(
                "Registries",
                listOf(Process(process, "PolicyCreated", "RegistryCreated")))
    }

    private fun createBillingModule(process: BusinessProcess): BaseModule {
        return BaseModule(
                "Billing",
                listOf(Process(process, "PolicyCreated", "AccountCreated")))
    }

    private fun createPolicyManagementModule(process: BusinessProcess): Module {
        return BaseModule(
                "PolicyManagement",
                listOf(Create(process, "PolicyCreated", BusinessProcessIdFactory())))
    }

    private fun assertQueueContains(eventQueue: EventQueue, event: Event) {
        assert(eventQueue.listEvents().find { it == event } != null)
    }
}