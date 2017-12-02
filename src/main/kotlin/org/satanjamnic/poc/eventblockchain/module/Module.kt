package org.satanjamnic.poc.eventblockchain.module

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event

interface Module : Listener, Publisher {
    fun name(): String
    fun process(businessProcess: BusinessProcess, vararg event: Event)
}