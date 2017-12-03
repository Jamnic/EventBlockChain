package org.satanjamnic.poc.eventblockchain.event.pool

import org.satanjamnic.poc.eventblockchain.event.Event

interface EventPool {
    operator fun plusAssign(event: Event)
    fun list() : MutableList<Event>
}