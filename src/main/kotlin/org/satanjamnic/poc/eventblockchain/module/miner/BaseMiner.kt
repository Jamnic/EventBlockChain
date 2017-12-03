package org.satanjamnic.poc.eventblockchain.module.miner

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.satanjamnic.poc.eventblockchain.businessprocess.BusinessProcess
import org.satanjamnic.poc.eventblockchain.event.Event
import org.satanjamnic.poc.eventblockchain.event.pool.EventPool
import org.satanjamnic.poc.eventblockchain.ledger.Ledger

class BaseMiner(
        private val eventPool: EventPool,
        private val ledger: Ledger,
        vararg private val businessProcesses: BusinessProcess
) : Miner {

    private var isIdle: Boolean = true

    override fun notifyObserver() {
        tryToCreateBlock()
    }

    private fun tryToCreateBlock() {
        runBlocking {
            val events = eventPool.list()

            if (isIdle && events.isNotEmpty()) {
                isIdle = false
                businessProcesses
                        .map { async { validateBusinessProcess(it, events) } }
                        .forEach { it.await() }

                tryToCreateBlock()
            }

            isIdle = true
        }
    }

    override fun isIdle(): Boolean {
        return isIdle
    }

    private fun validateBusinessProcess(process: BusinessProcess, events: List<Event>) {
        events.map { it.businessProcessId() }
                .toSet()
                .forEach { validateEventGroup(process, findEventGroupById(it, events)) }
    }

    private fun findEventGroupById(businessProcessId: Long, events: List<Event>): List<Event> {
        return events
                .filter { event -> event.businessProcessId() == businessProcessId }
                .toList()
    }

    private fun validateEventGroup(process: BusinessProcess, eventGroup: List<Event>) {
        if (process.validate(eventGroup.map { it.type() }))
            synchronized(eventPool, {
                if (eventPool.list().containsAll(eventGroup)) {
                    eventPool.removeEvents(eventGroup)
                    ledger.createBlock(eventGroup)
                }
            })
    }
}