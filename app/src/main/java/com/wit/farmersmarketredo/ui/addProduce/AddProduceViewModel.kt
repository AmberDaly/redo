package com.wit.farmersmarketredo.ui.addProduce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wit.farmersmarketredo.models.ProduceManager
import com.wit.farmersmarketredo.models.ProduceModel

class AddProduceViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addProduce(produce: ProduceModel) {
        status.value = try {
            ProduceManager.create(produce)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}