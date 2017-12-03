package org.satanjamnic.poc.eventblockchain

import org.junit.Test
import org.satanjamnic.poc.eventblockchain.businessprocess.BaseBusinessProcess
import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.event.pool.BaseEventPool
import org.satanjamnic.poc.eventblockchain.event.pool.EventPool
import org.satanjamnic.poc.eventblockchain.ledger.BaseLedger
import org.satanjamnic.poc.eventblockchain.ledger.Ledger
import org.satanjamnic.poc.eventblockchain.module.miner.BaseMiner
import org.satanjamnic.poc.eventblockchain.module.miner.Miner

class MinerSpec {

    @Test
    fun shouldValidateCompletenessOfBusinessProcess() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("A to B", "a", "b")
        val eventPool: EventPool = BaseEventPool()
        val ledger: Ledger = BaseLedger()
        val miner: Miner = BaseMiner(eventPool, ledger, process)

        eventPool.registerObserver(miner)

        // when
        eventPool.addEvent(BaseEvent("a", 1))
        eventPool.addEvent(BaseEvent("b", 1))

        while (eventPool.isNotEmpty() || !miner.isIdle() ) {
        }

        // then
        assert(ledger.blocks().size == 1)
    }

    @Test
    fun shouldCreateTwoBlocks() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("A to B", "a", "b")
        val eventPool: EventPool = BaseEventPool()
        val ledger: Ledger = BaseLedger()
        val miner: Miner = BaseMiner(eventPool, ledger, process)

        eventPool.registerObserver(miner)

        // when
        eventPool.addEvent(BaseEvent("a", 1))
        eventPool.addEvent(BaseEvent("b", 1))
        eventPool.addEvent(BaseEvent("a", 2))
        eventPool.addEvent(BaseEvent("b", 2))

        while (eventPool.isNotEmpty() || !miner.isIdle() ) {
        }

        // then
        assert(ledger.blocks().size == 2)
    }
}