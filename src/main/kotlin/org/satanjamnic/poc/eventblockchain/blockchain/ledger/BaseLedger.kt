package org.satanjamnic.poc.eventblockchain.blockchain.ledger

import org.satanjamnic.poc.eventblockchain.common.event.Event

class BaseLedger(
        private val blocks: MutableList<List<Event>> = mutableListOf()
) : Ledger {

    override fun blocks(): List<List<Event>> {
        return blocks
    }

    override fun createBlock(eventGroup: List<Event>) {
        println("Ledger creates block of ${eventGroup.size}")
        blocks += eventGroup
    }
}