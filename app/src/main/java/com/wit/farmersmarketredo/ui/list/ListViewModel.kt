package com.wit.farmersmarketredo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wit.farmersmarketredo.models.ProduceManager
import com.wit.farmersmarketredo.models.ProduceModel

import timber.log.Timber
import java.lang.Exception

class ListViewModel : ViewModel() {

    private val producesList =
        MutableLiveData<List<ProduceModel>>()

    val observableProducesList: LiveData<List<ProduceModel>>
        get() = producesList

    init { load() }

    fun load() {
        try {
            ProduceManager.findAll(producesList)
            Timber.i("Retrofit Load Success : $producesList.value")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Load Error : $e.message")
        }
    }

    fun delete(id: String) {
        try {
            ProduceManager.delete(id)
            Timber.i("Retrofit Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Delete Error : $e.message")
        }
    }
}

