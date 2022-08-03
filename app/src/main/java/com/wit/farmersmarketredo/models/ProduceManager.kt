package com.wit.farmersmarketredo.models

import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object ProduceManager : ProduceStore {

    val produces = ArrayList<ProduceModel>()

    override fun findAll(): List<ProduceModel> {
        return produces
    }

    override fun findById(id:Long) : ProduceModel? {
        val foundDonation: ProduceModel? = produces.find { it.id == id }
        return foundDonation
    }

    override fun create(donation: ProduceModel) {
        donation.id = getId()
        produces.add(donation)
        logAll()
    }

    fun logAll() {
        Timber.v("** Donations List **")
        produces.forEach { Timber.v("Donate ${it}") }
    }
}