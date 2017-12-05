package org.satanjamnic.poc.eventblockchain.module.miner

import org.satanjamnic.poc.eventblockchain.event.pool.Observer

interface Miner : Observer {
    fun isFree(): Boolean
}