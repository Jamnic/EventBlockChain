package org.satanjamnic.poc.eventblockchain.event.type

class EventType(
        private val type: String
) {

    override fun hashCode(): Int {
        return type.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is EventType && other.type == type
    }

    override fun toString(): String {
        return type
    }
}