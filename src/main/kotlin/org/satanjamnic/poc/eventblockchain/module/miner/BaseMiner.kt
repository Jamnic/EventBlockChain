package org.satanjamnic.poc.eventblockchain.module.miner

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

    private var isFree: Boolean = true

    override fun beNotified() {
        tryToExecuteTask()
    }

    private fun tryToExecuteTask() {
        synchronized(this, {
            runBlocking {
                if (isFree && eventPool.isNotEmpty()) {
                    isFree = false
                    execute()
                    isFree = true
                    tryToExecuteTask()
                }
            }
        })
    }

    override fun isFree(): Boolean {
        return isFree
    }

    private fun execute() {
        businessProcesses.forEach { validateBusinessProcess(it, eventPool.list()) }
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
        if (process.validate(eventGroup.map { it.type() })) {
            if (eventPool.list().containsAll(eventGroup)) {
                if (eventPool.removeAll(eventGroup))
                    ledger.createBlock(eventGroup)
            }
        }
    }

    override fun toString(): String {
        return "${if (isFree) "Idle" else "Busy"} Miner"
    }
}