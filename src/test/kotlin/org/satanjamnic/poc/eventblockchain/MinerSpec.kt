package org.satanjamnic.poc.eventblockchain

import org.junit.Test
import org.satanjamnic.poc.eventblockchain.blockchain.eventpool.BaseEventPool
import org.satanjamnic.poc.eventblockchain.blockchain.eventpool.EventPool
import org.satanjamnic.poc.eventblockchain.blockchain.eventpool.consumer.EventPoolConsumer
import org.satanjamnic.poc.eventblockchain.blockchain.eventpool.consumer.Miner
import org.satanjamnic.poc.eventblockchain.common.businessprocess.BaseBusinessProcess
import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.BaseEvent

class MinerSpec {

    @Test
    fun shouldConsumeIncomingEvents() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("A to B", "a", "b")
        val eventPool: EventPool = BaseEventPool()
        val consumer: EventPoolConsumer = Miner(eventPool, process)
        eventPool.registerObserver(consumer)

        // when
        eventPool.add(BaseEvent("a", 1))
        eventPool.add(BaseEvent("b", 1))
        eventPool.add(BaseEvent("a", 2))
        eventPool.add(BaseEvent("b", 2))

        // then
        while (eventPool.isNotEmpty() || consumer.isBlocked()) {
        }

        assert(eventPool.isEmpty())
    }

    @Test
    fun shouldDistributeEventsToTwoConsumers() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("A to B", "a", "b")
        val eventPool: EventPool = BaseEventPool()

        val firstConsumer: EventPoolConsumer = Miner(eventPool, process)
        val secondConsumer: EventPoolConsumer = Miner(eventPool, process)

        val consumers = listOf(firstConsumer, secondConsumer)

        eventPool.registerObserver(firstConsumer)
        eventPool.registerObserver(secondConsumer)

        // when
        eventPool.add(BaseEvent("a", 1))
        eventPool.add(BaseEvent("b", 1))
        eventPool.add(BaseEvent("a", 2))
        eventPool.add(BaseEvent("b", 2))
        eventPool.add(BaseEvent("a", 3))
        eventPool.add(BaseEvent("b", 3))
        eventPool.add(BaseEvent("a", 4))
        eventPool.add(BaseEvent("b", 4))
        eventPool.add(BaseEvent("a", 5))
        eventPool.add(BaseEvent("b", 5))

        // then
        while (eventPool.isNotEmpty() || consumers.any { it.isBlocked() }) {
        }

        assert(eventPool.isEmpty())
        assert(firstConsumer.consumed() > 0)
        assert(secondConsumer.consumed() > 0)
    }

//    @Test
//    fun shouldValidateCompletenessOfBusinessProcess() {
//        // given
//        val process: BusinessProcess = BaseBusinessProcess("A to B", "a", "b")
//        val eventPool: EventPool = BaseEventPool()
//        val ledger: Ledger = BaseLedger()
//        val miner: Miner = BaseMiner(eventPool, ledger, process)
//
//        eventPool.registerObserver(miner)
//
//        // when
//        eventPool.add(BaseEvent("a", 1))
//        eventPool.add(BaseEvent("b", 1))
//
//        while (eventPool.isNotEmpty() || !miner.isFree()) {
//        }
//
//        // then
//        assert(ledger.blocks().size == 1)
//    }
//
//    @Test
//    fun shouldCreateTwoBlocks() {
//        // given
//        val process: BusinessProcess = BaseBusinessProcess("A to B", "a", "b")
//        val eventPool: EventPool = BaseEventPool()
//        val ledger: Ledger = BaseLedger()
//        val miner: Miner = BaseMiner(eventPool, ledger, process)
//
//        eventPool.registerObserver(miner)
//
//        // when
//
//        eventPool.add(BaseEvent("a", 1))
//        eventPool.add(BaseEvent("b", 1))
//        eventPool.add(BaseEvent("a", 2))
//        eventPool.add(BaseEvent("b", 2))
//
//        // then
//
//        while(eventPool.isNotEmpty()) {}
////        while(!miner.isFree()) {}
//        assert(ledger.blocks().size == 2)
//    }
}