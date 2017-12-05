package org.satanjamnic.poc.eventblockchain.event.pool

interface Observable {
    fun registerObserver(observer: Observer)
    fun removeObserver(observer: Observer)
    fun notifyObservers()
}