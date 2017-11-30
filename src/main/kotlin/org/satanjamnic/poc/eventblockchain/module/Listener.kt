package org.satanjamnic.poc.eventblockchain.module

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessAction
import org.satanjamnic.poc.eventblockchain.event.Event

interface Listener {
    fun notify(businessAction: BusinessAction, event: Event)
}