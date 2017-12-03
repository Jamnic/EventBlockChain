package org.satanjamnic.poc.eventblockchain

import org.junit.Test
import org.satanjamnic.poc.eventblockchain.businessprocess.BaseBusinessProcess
import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.BaseEvent
import org.satanjamnic.poc.eventblockchain.event.pool.BaseEventPool
import org.satanjamnic.poc.eventblockchain.event.pool.EventPool
import org.satanjamnic.poc.eventblockchain.module.miner.BaseMiner
import org.satanjamnic.poc.eventblockchain.module.miner.Miner

class MinerSpec {

    @Test
    fun shouldValidateCompletenessOfBusinessProcess() {
        // given
        val process: BusinessProcess = BaseBusinessProcess("A to B", "a", "b")
        val eventPool: EventPool = BaseEventPool()
        val miner: Miner = BaseMiner(eventPool)

        // when
        eventPool += BaseEvent("a", 1)
        eventPool += BaseEvent("b", 1)

        // then
        miner.validate(process)
        assert(miner.blocks().size == 1)
    }
}