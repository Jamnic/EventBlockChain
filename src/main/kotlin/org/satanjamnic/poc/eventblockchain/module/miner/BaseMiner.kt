package org.satanjamnic.poc.eventblockchain.module.miner

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.pool.EventPool
import java.util.*

class BaseMiner(
        private val eventPool: EventPool,
        private val blocks: MutableList<List<Event>> = mutableListOf()
) : Miner {

    override fun validate(process: BusinessProcess) {
        val expectedResult = process.expectedResult()

        while(eventPool.list().isNotEmpty()) {
            val businessProcessId = randomElement(eventPool.list()).businessProcessId()

            val eventGroup = eventPool.list()
                    .filter { it.businessProcessId() == businessProcessId }

            if (eventGroup
                    .map { it.type() }
                    .containsAll(expectedResult)) {
                eventPool.list().removeAll(eventGroup)
                createBlock(eventGroup)
            }
        }
    }

    override fun blocks() : List<List<Event>> {
        return blocks
    }

    private fun randomElement(list: List<Event>): Event {
        return list[Random().nextInt(list.size)]
    }

    private fun createBlock(eventGroup: List<Event>) {
        blocks += eventGroup
    }
}