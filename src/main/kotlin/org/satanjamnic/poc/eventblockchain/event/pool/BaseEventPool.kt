package org.satanjamnic.poc.eventblockchain.event.pool

import org.satanjamnic.poc.eventblockchain.event.Event

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