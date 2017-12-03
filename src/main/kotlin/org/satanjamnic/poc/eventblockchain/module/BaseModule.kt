package org.satanjamnic.poc.eventblockchain.module

import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.businessprocess.step.BusinessProcessStep
import org.satanjamnic.poc.eventblockchain.database.BaseDatabase
import org.satanjamnic.poc.eventblockchain.database.Database
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.module.publisher.BasePublisher
import org.satanjamnic.poc.eventblockchain.module.publisher.Publisher

open class BaseModule(
        private val name: String,
        vararg private val businessProcessSteps: BusinessProcessStep,
        private val publisher: Publisher = BasePublisher(),
        private val database: Database = BaseDatabase()
) : Module, Publisher by publisher {

    override fun name(): String {
        return name
    }

    override fun process(businessProcess: BusinessProcess, vararg event: Event) {
        val businessProcessStep = businessProcessSteps
                .find { businessProcess == it.businessAction() }

        database.save("consumed", *event)

        if (businessProcessStep != null) {
            val producedEvent = produceEvent(businessProcessStep, *event)

            database.save("produced", producedEvent)

            findQueueToPublish(businessProcessStep)
                    .publish(businessProcess, producedEvent)
        }
    }

    override fun notify(businessProcess: BusinessProcess, event: Event) {
        process(businessProcess, event)
    }

    override fun listEvents(tableName: String): List<Event> {
        return database.list(tableName)
    }

    override fun toString(): String {
        return "$name Module"
    }

    private fun produceEvent(businessProcessStep: BusinessProcessStep, vararg events: Event): Event {
        return businessProcessStep.consumeEventsAndCreateNewEvent(*events)
    }
}