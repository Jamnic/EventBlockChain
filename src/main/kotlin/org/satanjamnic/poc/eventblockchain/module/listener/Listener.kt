package org.satanjamnic.poc.eventblockchain.module.listener

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event

interface Listener {
    fun notify(businessProcess: BusinessProcess, event: Event)
}