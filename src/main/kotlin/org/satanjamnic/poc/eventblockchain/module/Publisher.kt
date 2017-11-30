package org.satanjamnic.poc.eventblockchain.module

import org.satanjamnic.poc.eventblockchain.event.queue.EventQueue

interface Publisher {
    // TODO consider using a EventType/EventQueue map
    fun publishesTo(eventQueue: EventQueue)
}