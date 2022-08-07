package com.wit.farmersmarketredo.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.wit.farmersmarketredo.firebase.FirebaseDBManager
import com.wit.farmersmarketredo.models.ProduceModel
import timber.log.Timber

class ListViewModel : ViewModel() {


    private val producesList =
        MutableLiveData<List<ProduceModel>>()

    val observableProducesList: LiveData<List<ProduceModel>>
        get() = producesList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    var readOnly = MutableLiveData(false)

    var searchResults = ArrayList<ProduceModel>()

    init { load() }

    fun load() {
        try {
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!,
                producesList)
            Timber.i("Report Load Success : ${producesList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report Load Error : $e.message")
        }
    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(producesList)
            Timber.i("Report LoadAll Success : ${producesList.value.toString()}")
        }
        catch (e: java.lang.Exception) {
            Timber.i("Report LoadAll Error : $e.message")
        }
    }

    fun delete(userid: String, id: String) {
        try {
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}