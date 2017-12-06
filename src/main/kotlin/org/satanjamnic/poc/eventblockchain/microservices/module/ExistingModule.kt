package org.satanjamnic.poc.eventblockchain.microservices.module

import org.satanjamnic.poc.eventblockchain.common.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.common.event.Event
import org.satanjamnic.poc.eventblockchain.microservices.businessprocess.step.BusinessProcessStep
import org.satanjamnic.poc.eventblockchain.microservices.database.BaseDatabase
import org.satanjamnic.poc.eventblockchain.microservices.database.Database
import org.satanjamnic.poc.eventblockchain.microservices.queue.publisher.BaseEventQueuePublisher
import org.satanjamnic.poc.eventblockchain.microservices.queue.publisher.EventQueuePublisher

open class ExistingModule(
        private val name: String,
        vararg private val businessProcessSteps: BusinessProcessStep,
        private val publisher: EventQueuePublisher = BaseEventQueuePublisher(),
        private val database: Database = BaseDatabase()
) : Module, EventQueuePublisher by publisher {

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