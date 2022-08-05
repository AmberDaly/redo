package com.wit.farmersmarketredo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wit.farmersmarketredo.models.ProduceManager
import com.wit.farmersmarketredo.models.ProduceModel
import timber.log.Timber

class ListViewModel : ViewModel() {


    private val producesList = MutableLiveData<List<ProduceModel>>()

    val observableProducesList: LiveData<List<ProduceModel>>
        get() = producesList

    init {
        load()
    }

    fun load() {
        try {
            ProduceManager.findAll(producesList)
            Timber.i("Retrofit Success : $producesList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Error : $e.message")
        }
    }
}