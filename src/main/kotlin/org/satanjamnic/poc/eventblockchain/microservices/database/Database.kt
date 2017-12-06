package org.satanjamnic.poc.eventblockchain.microservices.database

import org.satanjamnic.poc.eventblockchain.common.event.Event

interface Database {
    fun save(tableName: String, vararg event: Event)
    fun list(tableName: String): List<Event>
}