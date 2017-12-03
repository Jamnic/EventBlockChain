package org.satanjamnic.poc.eventblockchain.ledger

import org.satanjamnic.poc.eventblockchain.event.Event

interface Ledger {
    fun blocks(): List<List<Event>>
    fun createBlock(eventGroup: List<Event>)
}