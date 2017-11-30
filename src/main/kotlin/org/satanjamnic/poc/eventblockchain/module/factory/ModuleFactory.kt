package org.satanjamnic.poc.eventblockchain.module.factory

import org.satanjamnic.poc.eventblockchain.module.Module
import org.satanjamnic.poc.eventblockchain.ModuleReceipt

interface ModuleFactory {
    fun create(moduleReceipt: ModuleReceipt): Module
}