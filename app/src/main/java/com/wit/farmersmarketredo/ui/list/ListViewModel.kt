package com.wit.farmersmarketredo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wit.farmersmarketredo.models.ProduceManager
import com.wit.farmersmarketredo.models.ProduceModel

class ListViewModel : ViewModel() {


    private val producesList = MutableLiveData<List<ProduceModel>>()

    val observableDonationsList: LiveData<List<ProduceModel>>
        get() = producesList

    init {
        load()
    }

    fun load() {
        producesList.value = ProduceManager.findAll()
    }
}