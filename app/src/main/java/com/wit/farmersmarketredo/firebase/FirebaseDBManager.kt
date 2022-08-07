package com.wit.farmersmarketredo.firebase


import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.wit.farmersmarketredo.models.ProduceModel
import com.wit.farmersmarketredo.models.ProduceStore

import timber.log.Timber

object FirebaseDBManager : ProduceStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(producesList: MutableLiveData<List<ProduceModel>>) {
        database.child("produces")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Produce error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<ProduceModel>()
                    val children = snapshot.children
                    children.forEach {
                        val produce = it.getValue(ProduceModel::class.java)
                        localList.add(produce!!)
                    }
                    database.child("produces")
                        .removeEventListener(this)

                    producesList.value = localList
                }
            })
    }

    override fun findAll(userid: String, producesList: MutableLiveData<List<ProduceModel>>) {

        database.child("user-produces").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Produce error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<ProduceModel>()
                    val children = snapshot.children
                    children.forEach {
                        val produce = it.getValue(ProduceModel::class.java)
                        localList.add(produce!!)
                    }
                    database.child("user-produces").child(userid)
                        .removeEventListener(this)

                    producesList.value = localList
                }
            })
    }

    override fun findById(userid: String, produceid: String, produce: MutableLiveData<ProduceModel>) {

        database.child("user-produces").child(userid)
            .child(produceid).get().addOnSuccessListener {
                produce.value = it.getValue(ProduceModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, produce: ProduceModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("produces").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        produce.uid = key
        val produceValues = produce.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/produces/$key"] = produceValues
        childAdd["/user-produces/$uid/$key"] = produceValues

        database.updateChildren(childAdd)
    }

    override fun delete(userid: String, produceid: String) {

        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/produces/$produceid"] = null
        childDelete["/user-produces/$userid/$produceid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, produceid: String, produce: ProduceModel) {

        val produceValues = produce.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["produces/$produceid"] = produceValues
        childUpdate["user-produces/$userid/$produceid"] = produceValues

        database.updateChildren(childUpdate)
    }

    fun updateImageRef(userid: String,imageUri: String) {

        val userProduces = database.child("user-produces").child(userid)
        val allProduces = database.child("produces")

        userProduces.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        //Update Users imageUri
                        it.ref.child("profilepic").setValue(imageUri)
                        //Update all donations that match 'it'
                        val produce = it.getValue(ProduceModel::class.java)
                        allProduces.child(produce!!.uid!!)
                            .child("profilepic").setValue(imageUri)
                    }
                }
            })
    }
}