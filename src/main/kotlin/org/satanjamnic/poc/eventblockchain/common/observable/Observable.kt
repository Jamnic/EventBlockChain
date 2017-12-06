package org.satanjamnic.poc.eventblockchain.common.observable

import org.satanjamnic.poc.eventblockchain.common.observer.Observer

interface Observable {
    fun registerObserver(observer: Observer)
    fun removeObserver(observer: Observer)
    fun notifyObservers()
}