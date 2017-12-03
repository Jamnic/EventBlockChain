package org.satanjamnic.poc.eventblockchain.module

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.module.listener.Listener
import org.satanjamnic.poc.eventblockchain.module.publisher.Publisher

interface Module : Listener, Publisher {
    fun name(): String
    fun process(businessProcess: BusinessProcess, vararg event: Event)
}