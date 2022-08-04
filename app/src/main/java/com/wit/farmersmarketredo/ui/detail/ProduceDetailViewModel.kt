package com.wit.farmersmarketredo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wit.farmersmarketredo.models.ProduceManager
import com.wit.farmersmarketredo.models.ProduceModel


class ProduceDetailViewModel : ViewModel() {
    private val produce = MutableLiveData<ProduceModel>()

    val observableProduce: LiveData<ProduceModel>
        get() = produce

    fun getProduce(id: String) {
        produce.value = ProduceManager.findById(id)
    }
}

