package com.wit.farmersmarketredo.models

import androidx.lifecycle.MutableLiveData

interface ProduceStore {
    fun findAll(producesList: MutableLiveData<List<ProduceModel>>)
    fun findById(id: Long) : ProduceModel?
    fun create(produce: ProduceModel)
}