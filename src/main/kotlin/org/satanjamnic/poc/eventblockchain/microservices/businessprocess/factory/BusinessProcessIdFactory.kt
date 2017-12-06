package org.satanjamnic.poc.eventblockchain.microservices.businessprocess.factory

class BusinessProcessIdFactory {

    private var currentId: Long = 1

    fun next(): Long {
        return currentId++
    }
}