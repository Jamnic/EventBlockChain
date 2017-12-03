package org.satanjamnic.poc.eventblockchain.module.miner

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.pool.EventPool
import org.satanjamnic.poc.eventblockchain.ledger.Ledger
import java.util.*

class BaseMiner(
        private val eventPool: EventPool,
        private val ledger: Ledger
) : Miner {

    override fun validate(process: BusinessProcess) {
        while (eventPool.list().isNotEmpty()) {
            val eventGroup = pickEventGroup()

            if (process.validate(eventGroup.map { it.type() })) {
                eventPool.list().removeAll(eventGroup)
                ledger.createBlock(eventGroup)
            }
        }
    }

    private fun pickEventGroup(): List<Event> {
        val businessProcessId = randomElement(eventPool.list()).businessProcessId()

        val eventGroup = eventPool.list()
                .filter { it.businessProcessId() == businessProcessId }
        return eventGroup
    }

    private fun randomElement(list: List<Event>): Event {
        return list[Random().nextInt(list.size)]
    }
}