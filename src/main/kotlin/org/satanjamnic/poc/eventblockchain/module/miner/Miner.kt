package org.satanjamnic.poc.eventblockchain.module.miner

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event

interface Miner {
    fun validate(process: BusinessProcess)
    fun blocks(): List<List<Event>>
}