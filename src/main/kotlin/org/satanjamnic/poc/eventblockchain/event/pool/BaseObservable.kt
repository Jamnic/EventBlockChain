package org.satanjamnic.poc.eventblockchain.event.pool

class BaseObservable(
        private val observers: MutableList<Observer> = mutableListOf()
) : Observable {

    override fun registerObserver(observer: Observer) {
        this.observers += observer
    }

    override fun removeObserver(observer: Observer) {
        this.observers -= observer
    }
}