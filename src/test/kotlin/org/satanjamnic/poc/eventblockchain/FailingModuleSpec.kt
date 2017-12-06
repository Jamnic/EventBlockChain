package org.satanjamnic.poc.eventblockchain

import org.junit.Test
import org.satanjamnic.poc.eventblockchain.common.businessprocess.BaseBusinessProcess
import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.microservices.businessprocess.factory.BusinessProcessIdFactory
import org.satanjamnic.poc.eventblockchain.microservices.businessprocess.step.Create
import org.satanjamnic.poc.eventblockchain.microservices.businessprocess.step.Process
import org.satanjamnic.poc.eventblockchain.microservices.module.ExistingModule
import org.satanjamnic.poc.eventblockchain.microservices.module.FailingModule
import org.satanjamnic.poc.eventblockchain.microservices.module.Module
import org.satanjamnic.poc.eventblockchain.microservices.queue.EventQueue
import org.satanjamnic.poc.eventblockchain.microservices.queue.ExistingEventQueue
import org.satanjamnic.poc.eventblockchain.microservices.queue.FailingEventQueue

class FailingModuleSpec {


    @Test
    fun shouldNotPublishToEventQueue() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("First process")

        val moduleA: Module = FailingModule("A",
                Create(process, "a", BusinessProcessIdFactory()))

        val queueA: EventQueue = ExistingEventQueue("PolicyCreated")
        moduleA.publishesTo(queueA)

        // when
        moduleA.process(process)

        // then
        assert(queueA.listEvents().isEmpty())
        assert(moduleA.listEvents("produced").isEmpty())
    }

    @Test
    fun shouldFailToNotifyListeners() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("First process")

        val moduleA: Module = ExistingModule("A",
                Create(process, "a", BusinessProcessIdFactory()))
        val moduleB: Module = ExistingModule("B",
                Process(process, "a", "b"))

        val failingQueueA: EventQueue = FailingEventQueue("a")
        moduleA.publishesTo(failingQueueA)
        failingQueueA.registerListener(moduleB)

        val queueB: EventQueue = ExistingEventQueue("b")
        moduleB.publishesTo(queueB)

        // when
        moduleA.process(process)

        // then
        assertQueueContains(failingQueueA, BaseEvent("a", 1))

        assert(queueB.listEvents().isEmpty())
        assert(moduleB.listEvents("consumed").isEmpty())
        assert(moduleB.listEvents("produced").isEmpty())
    }

    private fun assertQueueContains(eventQueue: EventQueue, event: Event) {
        assert(eventQueue.listEvents().find { it == event } != null)
    }
}