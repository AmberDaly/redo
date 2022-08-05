package com.wit.farmersmarketredo.models

import androidx.lifecycle.MutableLiveData
import com.wit.farmersmarketredo.api.ProduceClient
import com.wit.farmersmarketredo.api.ProduceWrapper
import retrofit2.*
import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object ProduceManager : ProduceStore {

    val produces = ArrayList<ProduceModel>()

    override fun findAll(producesList: MutableLiveData<List<ProduceModel>>) {

        val call = ProduceClient.getApi().getall()

        call.enqueue(object : Callback<List<ProduceModel>> {
            override fun onResponse(call: Call<List<ProduceModel>>,
                                    response: Response<List<ProduceModel>>
            ) {
                producesList.value = response.body() as ArrayList<ProduceModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<ProduceModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    override fun findById(id:Long) : ProduceModel? {
        val foundDonation: ProduceModel? = produces.find { it.id == id }
        return foundDonation
    }

    override fun create(produce: ProduceModel) {

        val call = ProduceClient.getApi().post(produce)

        call.enqueue(object : Callback<ProduceWrapper> {
            override fun onResponse(call: Call<ProduceWrapper>,
                                    response: Response<ProduceWrapper>
            ) {
                val produceWrapper = response.body()
                if (produceWrapper != null) {
                    Timber.i("Retrofit ${produceWrapper.message}")
                    Timber.i("Retrofit ${produceWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<ProduceWrapper>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }

    fun logAll() {
        Timber.v("** Donations List **")
        produces.forEach { Timber.v("Donate ${it}") }
    }
}