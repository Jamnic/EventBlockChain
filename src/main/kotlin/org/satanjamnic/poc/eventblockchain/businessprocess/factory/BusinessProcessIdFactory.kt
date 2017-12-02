package org.satanjamnic.poc.eventblockchain.businessprocess.factory

class BusinessProcessIdFactory {

    private var currentId: Long = 1

    fun next(): Long {
        return currentId++
    }
}