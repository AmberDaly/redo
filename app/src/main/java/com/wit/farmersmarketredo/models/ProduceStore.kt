package com.wit.farmersmarketredo.models

interface ProduceStore {
    fun findAll() : List<ProduceModel>
    fun findById(id: Long) : ProduceModel?
    fun create(donation: ProduceModel)
}