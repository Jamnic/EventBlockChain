package org.satanjamnic.poc.eventblockchain.event

import org.satanjamnic.poc.eventblockchain.event.type.EventType

class BaseEvent(
        private val type: EventType,
        private val businessProcessId: Long
) : Event {

    constructor(
            typeName: String,
            businessProcessId: Long
    ) : this(
            EventType(typeName),
            businessProcessId)

    override fun type(): EventType {
        return type
    }

    override fun businessProcessId(): Long {
        return businessProcessId
    }

    override fun toString(): String {
        return type.toString()
    }

    override fun equals(other: Any?): Boolean {
        return other is BaseEvent &&
                other.type == type &&
                other.businessProcessId == businessProcessId
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + businessProcessId.hashCode()
        return result
    }
}