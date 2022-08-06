package com.wit.farmersmarketredo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wit.farmersmarketredo.firebase.FirebaseDBManager
import com.wit.farmersmarketredo.models.ProduceModel
import timber.log.Timber
import java.lang.Exception

class ProduceDetailViewModel : ViewModel() {
    private val produce = MutableLiveData<ProduceModel>()

    var observableProduce: LiveData<ProduceModel>
        get() = produce
        set(value) {produce.value = value.value}

    fun getProduce(userid:String, id: String) {
        try {
            FirebaseDBManager.findById(userid, id, produce)
            Timber.i("Detail getProduce() Success : ${produce.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Detail getProduce() Error : $e.message")
        }
    }

    fun updateProduce(userid:String, id: String,produce: ProduceModel) {
        try {
            FirebaseDBManager.update(userid, id, produce)
            Timber.i("Detail update() Success : $produce")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}