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
        val process: BusinessProcess = BaseBusinessProcess("First process")

        val moduleA: Module = FailingModule("A",
                Create(process, "a", BusinessProcessIdFactory()))

        val queueA: EventQueue = BaseEventQueue("PolicyCreated")
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

        val moduleA: Module = BaseModule("A",
                Create(process, "a", BusinessProcessIdFactory()))
        val moduleB: Module = BaseModule("B",
                Process(process, "a", "b"))

        val failingQueueA: EventQueue = FailingEventQueue("a")
        moduleA.publishesTo(failingQueueA)
        failingQueueA.registerListener(moduleB)

        val queueB: EventQueue = BaseEventQueue("b")
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