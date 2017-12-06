package org.satanjamnic.poc.eventblockchain.common.observable

import org.satanjamnic.poc.eventblockchain.common.observer.Observer

class BaseObservable(
        private val observers: MutableList<Observer> = mutableListOf()
) : Observable {

    override fun registerObserver(observer: Observer) {
        this.observers += observer
    }

    override fun removeObserver(observer: Observer) {
        this.observers -= observer
    }

    override fun notifyObservers() {
        observers.forEach { it.beNotified() }
    }
}