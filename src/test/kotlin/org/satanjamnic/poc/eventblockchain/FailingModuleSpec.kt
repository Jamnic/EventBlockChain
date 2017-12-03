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
import org.satanjamnic.poc.eventblockchain.event.queue.failing.FailingEventQueue
import org.satanjamnic.poc.eventblockchain.module.BaseModule
import org.satanjamnic.poc.eventblockchain.module.Module
import org.satanjamnic.poc.eventblockchain.module.failing.FailingModule

class FailingModuleSpec {


    @Test
    fun shouldNotPublishToEventQueue() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("Create policy")

        val failingPolicyManagement: Module = FailingModule(
                "PolicyManagement",
                listOf(Create(process, "PolicyCreated", BusinessProcessIdFactory())))

        val policyCreatedQueue: EventQueue = BaseEventQueue("PolicyCreated")
        failingPolicyManagement.publishesTo(policyCreatedQueue)

        // when
        failingPolicyManagement.process(process)

        // then
        assert(policyCreatedQueue.listEvents().isEmpty())
    }

    @Test
    fun shouldFailToNotifyListeners() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("Create policy")

        val policyManagement: Module = BaseModule(
                "PolicyManagement",
                listOf(Create(process, "PolicyCreated", BusinessProcessIdFactory())))
        val billing: Module = BaseModule(
                "Billing",
                listOf(Process(process, "PolicyCreated", "AccountCreated")))

        val failingPolicyCreatedQueue: EventQueue = FailingEventQueue("PolicyCreated")
        policyManagement.publishesTo(failingPolicyCreatedQueue)
        failingPolicyCreatedQueue.registerListener(billing)

        val accountCreatedQueue: EventQueue = BaseEventQueue("AccountCreated")
        billing.publishesTo(accountCreatedQueue)

        // when
        policyManagement.process(process)

        // then
        assertQueueContains(failingPolicyCreatedQueue, BaseEvent("PolicyCreated", 1))
        assert(accountCreatedQueue.listEvents().isEmpty())
    }

    private fun assertQueueContains(eventQueue: EventQueue, event: Event) {
        assert(eventQueue.listEvents().find { it == event } != null)
    }
}