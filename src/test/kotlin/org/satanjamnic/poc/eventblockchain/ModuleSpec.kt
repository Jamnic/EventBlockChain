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
        val process: BusinessProcess = BaseBusinessProcess("First process")
        val moduleA: Module = BaseModule("A",
                Create(process, "a", BusinessProcessIdFactory()))
        val queueA: EventQueue = BaseEventQueue("a")
        moduleA.publishesTo(queueA)

        // when
        moduleA.process(process)

        // then
        val createdEvent = BaseEvent("a", 1)

        assertQueueContains(queueA, createdEvent)
        assertDatabaseContains(moduleA, "produced", createdEvent)
    }

    @Test
    fun shouldProcessCreatedEvent() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("First process")

        val moduleA: Module = BaseModule("A",
                Create(process, "a", BusinessProcessIdFactory()))
        val moduleB: Module = BaseModule("B",
                Process(process, "a", "b"))

        val queueA: EventQueue = BaseEventQueue("a")
        moduleA.publishesTo(queueA)
        queueA.registerListener(moduleB)

        val queueB: EventQueue = BaseEventQueue("b")
        moduleB.publishesTo(queueB)

        // when
        moduleA.process(process)

        // then
        val consumedEvent = BaseEvent("a", 1)
        val processedEvent = BaseEvent("b", 1)

        assertQueueContains(queueB, processedEvent)
        assertDatabaseContains(moduleB, "consumed", consumedEvent)
        assertDatabaseContains(moduleB, "produced", processedEvent)
    }


    @Test
    fun shouldPublishToMultipleModules() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("First process")

        val moduleA: Module = BaseModule("A",
                Create(process, "a", BusinessProcessIdFactory()))
        val moduleB: Module = BaseModule("B",
                Process(process, "a", "b"))
        val moduleC: Module = BaseModule("C",
                Process(process, "a", "c"))

        val queueA: EventQueue = BaseEventQueue("a")
        moduleA.publishesTo(queueA)
        queueA.registerListener(moduleB)
        queueA.registerListener(moduleC)

        val queueB: EventQueue = BaseEventQueue("b")
        moduleB.publishesTo(queueB)

        val queueC: EventQueue = BaseEventQueue("c")
        moduleC.publishesTo(queueC)

        // when
        moduleA.process(process)

        // then
        val consumedEventA = BaseEvent("a", 1)
        val producedEventB = BaseEvent("b", 1)
        val producedEventC = BaseEvent("c", 1)

        assertQueueContains(queueB, producedEventB)
        assertDatabaseContains(moduleB, "consumed", consumedEventA)
        assertDatabaseContains(moduleB, "produced", producedEventB)

        assertQueueContains(queueC, producedEventC)
        assertDatabaseContains(moduleC, "consumed", consumedEventA)
        assertDatabaseContains(moduleC, "produced", producedEventC)
    }

    @Test
    fun shouldNotMixBusinessProcesses() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("First process")

        val moduleA: Module = BaseModule("A",
                Create(process, "a", BusinessProcessIdFactory()))
        val moduleB: Module = BaseModule("B",
                Process(process, "a", "b"))
        val moduleC: Module = BaseModule("C",
                Process(process, "a", "c"))

        val queueA: EventQueue = BaseEventQueue("a")
        moduleA.publishesTo(queueA)
        queueA.registerListener(moduleB)
        queueA.registerListener(moduleC)

        val queueB: EventQueue = BaseEventQueue("b")
        moduleB.publishesTo(queueB)

        // when
        moduleA.process(process)
        moduleA.process(process)

        // then
        val consumedEvent1 = BaseEvent("a", 1)
        val producedEvent1 = BaseEvent("b", 1)
        val consumedEvent2 = BaseEvent("a", 2)
        val producedEvent2 = BaseEvent("b", 2)

        assertDatabaseContains(moduleB, "consumed", consumedEvent1)
        assertDatabaseContains(moduleB, "produced", producedEvent1)

        assertQueueContains(queueA, consumedEvent1)
        assertQueueContains(queueB, producedEvent1)

        assertDatabaseContains(moduleB, "consumed", consumedEvent2)
        assertDatabaseContains(moduleB, "produced", producedEvent2)

        assertQueueContains(queueA, consumedEvent2)
        assertQueueContains(queueB, producedEvent2)
    }

    private fun assertQueueContains(eventQueue: EventQueue, event: Event) {
        assert(eventQueue.listEvents().find { it == event } != null)
    }

    private fun assertDatabaseContains(module: Module, tableName: String, event: Event) {
        assert(module.listEvents(tableName).find { it == event } != null)
    }
}