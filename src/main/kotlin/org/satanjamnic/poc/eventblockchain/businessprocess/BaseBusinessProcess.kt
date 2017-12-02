package org.satanjamnic.poc.eventblockchain.businessprocess

class BaseBusinessProcess(
        private val name: String
) : BusinessProcess {

    override fun name(): String {
        return name
    }

    override fun toString(): String {
        return name
    }
}