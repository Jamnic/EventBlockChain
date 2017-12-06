package org.satanjamnic.poc.eventblockchain.microservices.queue.listener

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event

interface EventQueueListener {
    fun notify(businessProcess: BusinessProcess, event: Event)
}