package org.satanjamnic.poc.eventblockchain.event.pool

import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.satanjamnic.poc.eventblockchain.event.Event

class BaseEventPool(
        private val events: MutableList<Event> = mutableListOf(),
        private val observers: MutableList<Observer> = mutableListOf(),
        private val observable: Observable = BaseObservable(observers)
) : EventPool, Observable by observable {
    override fun addEvent(event: Event) {
        events += event

        runBlocking {
            launch {
                observers.forEach { it.notifyObserver() }
            }
        }
    }

    override fun list(): List<Event> {
        return ArrayList(events)
    }

    override fun isNotEmpty(): Boolean {
        return events.isNotEmpty()
    }

    override fun removeEvents(eventGroup: List<Event>) : Boolean {
        return events.removeAll(eventGroup)
    }
}