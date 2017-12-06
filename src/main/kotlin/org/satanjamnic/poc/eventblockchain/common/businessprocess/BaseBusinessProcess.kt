package org.satanjamnic.poc.eventblockchain.common.businessprocess

import org.satanjamnic.poc.eventblockchain.common.event.type.EventType

class BaseBusinessProcess(
        private val name: String,
        private val expectedResults: List<EventType>
) : BusinessProcess {

    constructor(
            name: String,
            vararg expectedResultsName: String
    ) : this(name, expectedResultsName.map { EventType(it) })

    override fun name(): String {
        return name
    }

    override fun validate(eventGroup: List<EventType>): Boolean {
        return eventGroup.containsAll(expectedResults)
    }

    override fun toString(): String {
        return name
    }
}