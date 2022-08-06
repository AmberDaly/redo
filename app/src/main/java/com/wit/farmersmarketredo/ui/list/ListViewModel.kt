package com.wit.farmersmarketredo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.wit.farmersmarketredo.firebase.FirebaseDBManager
import com.wit.farmersmarketredo.models.ProduceModel
import timber.log.Timber

class ListViewModel : ViewModel() {


    private val producesList = MutableLiveData<List<ProduceModel>>()

    val observableProducesList: LiveData<List<ProduceModel>>
        get() = producesList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {
        load()
    }

    fun load() {
        try {
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,producesList)
            Timber.i("List Load Success : ${producesList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Load Error : $e.message")
        }
    }
    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("Retrofit Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Retrofit Delete Error : $e.message")
        }
    }
}