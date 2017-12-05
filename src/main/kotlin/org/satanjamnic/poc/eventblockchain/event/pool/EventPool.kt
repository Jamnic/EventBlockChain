package org.satanjamnic.poc.eventblockchain.event.pool

import org.satanjamnic.poc.eventblockchain.event.Event

interface EventPool : Observable, MutableCollection<Event> {
    fun list() : List<Event>
}