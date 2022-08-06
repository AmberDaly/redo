package com.wit.farmersmarketredo.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface ProduceStore {
    fun findAll(producesList:
                MutableLiveData<List<ProduceModel>>)
    fun findAll(userid:String,
                producesList:
                MutableLiveData<List<ProduceModel>>)
    fun findById(userid:String, produceid: String,
                 produce: MutableLiveData<ProduceModel>)
    fun create(firebaseUser: MutableLiveData<FirebaseUser>, produce: ProduceModel)
    fun delete(userid:String, produceid: String)
    fun update(userid:String, produceid: String, produce: ProduceModel)
}