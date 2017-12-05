package org.satanjamnic.poc.eventblockchain

import org.junit.Test
import org.satanjamnic.poc.eventblockchain.businessprocess.BaseBusinessProcess
import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.event.pool.BaseEventPool
import org.satanjamnic.poc.eventblockchain.event.pool.EventPool
import org.satanjamnic.poc.eventblockchain.event.pool.consumer.EventPoolConsumer

class MinerSpec {

    @Test
    fun shouldConsumeIncomingEvents() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("A to B", "a", "b")
        val eventPool: EventPool = BaseEventPool()
        val consumer = EventPoolConsumer(eventPool, process)
        eventPool.registerObserver(consumer)

        // when
        eventPool.add(BaseEvent("a", 1))
        eventPool.add(BaseEvent("b", 1))
        eventPool.add(BaseEvent("a", 2))
        eventPool.add(BaseEvent("b", 2))

        // then
        assert(eventPool.isEmpty())
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