package org.satanjamnic.poc.eventblockchain.microservices.database

import org.satanjamnic.poc.eventblockchain.common.event.Event

class BaseDatabase(
        private val eventDatabase: MutableMap<String, MutableList<Event>> = mutableMapOf()
) : Database {

    override fun save(tableName: String, vararg event: Event) {
        val table = this.eventDatabase[tableName]
        if (table == null)
            this.eventDatabase[tableName] = mutableListOf(*event)
        else
            table.addAll(event)
    }

    override fun list(tableName: String): List<Event> {
        return eventDatabase[tableName] ?: listOf()
    }
}