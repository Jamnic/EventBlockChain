package org.satanjamnic.poc.eventblockchain.module

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessAction
import org.satanjamnic.poc.eventblockchain.businessprocess.step.BusinessProcessStep
import org.satanjamnic.poc.eventblockchain.event.Event

interface Module : Listener, Publisher {
    fun name(): String
    fun businessSteps(): List<BusinessProcessStep>
    fun process(businessAction: BusinessAction, vararg event: Event)
}