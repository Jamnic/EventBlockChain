package org.satanjamnic.poc.eventblockchain.blockchain.eventpool

import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.common.observable.Observable

interface EventPool : Observable, MutableCollection<Event> {
    fun list(): List<Event>
}