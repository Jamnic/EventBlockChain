package org.satanjamnic.poc.eventblockchain.blockchain.ledger

import org.satanjamnic.poc.eventblockchain.common.event.Event

interface Ledger {
    fun blocks(): List<List<Event>>
    fun createBlock(eventGroup: List<Event>)
}