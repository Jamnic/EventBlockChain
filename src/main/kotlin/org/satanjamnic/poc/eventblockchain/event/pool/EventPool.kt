package org.satanjamnic.poc.eventblockchain.event.pool

import org.satanjamnic.poc.eventblockchain.event.Event

interface EventPool : Observable {
    fun addEvent(event: Event)
    fun list() : List<Event>
    fun isNotEmpty(): Boolean
    fun removeEvents(eventGroup: List<Event>) : Boolean
}