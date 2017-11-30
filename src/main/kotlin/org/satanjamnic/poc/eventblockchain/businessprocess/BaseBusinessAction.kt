package org.satanjamnic.poc.eventblockchain.businessprocess

class BaseBusinessAction(
        private val name: String
) : BusinessAction {

    override fun name(): String {
        return name
    }

    override fun toString(): String {
        return name
    }
}