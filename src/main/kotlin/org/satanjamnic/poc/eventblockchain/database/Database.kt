package org.satanjamnic.poc.eventblockchain.database

import org.satanjamnic.poc.eventblockchain.event.Event

interface Database {
    fun save(tableName: String, vararg event: Event)
    fun list(tableName: String): List<Event>
}