package org.satanjamnic.poc.eventblockchain.blockchain.eventpool.consumer

import org.satanjamnic.poc.eventblockchain.common.observer.Observer

interface EventPoolConsumer : Observer {
    fun consumed(): Int
    fun isBlocked(): Boolean
}