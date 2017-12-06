package org.satanjamnic.poc.eventblockchain.blockchain.eventpool

import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.common.observable.BaseObservable
import org.satanjamnic.poc.eventblockchain.common.observable.Observable

class BaseEventPool(
        private val events: MutableList<Event> = mutableListOf(),
        private val observable: Observable = BaseObservable()
) :
        EventPool,
        MutableCollection<Event> by events,
        Observable by observable {

    override fun add(element: Event): Boolean {
        events += element
        observable.notifyObservers()
        return true
    }

    override fun list(): List<Event> {
        return ArrayList(events)
    }

    override fun removeAll(elements: Collection<Event>): Boolean {
        synchronized(this, {
            return if (events.containsAll(elements)) {
                events.removeAll(elements)
                true
            } else
                false
        })
    }
}