package org.satanjamnic.poc.eventblockchain.ledger

import org.satanjamnic.poc.eventblockchain.event.Event

class BaseLedger(
        private val blocks: MutableList<List<Event>> = mutableListOf()
) : Ledger {

    override fun blocks(): List<List<Event>> {
        return blocks
    }

    override fun createBlock(eventGroup: List<Event>) {
        blocks += eventGroup
    }
}