package com.wit.farmersmarketredo.models

import androidx.lifecycle.MutableLiveData
import com.wit.farmersmarketredo.api.ProduceClient
import com.wit.farmersmarketredo.api.ProduceWrapper

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

object ProduceManager : ProduceStore {

    private var produces = ArrayList<ProduceModel>()

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

    override fun findById(id:String) : ProduceModel? {
        val foundProduce: ProduceModel? = produces.find { it._id == id }
        return foundProduce
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

    override fun delete(id: String) {
        val call = ProduceClient.getApi().delete(id)

        call.enqueue(object : Callback<ProduceWrapper> {
            override fun onResponse(call: Call<ProduceWrapper>,
                                    response: Response<ProduceWrapper>
            ) {
                val produceWrapper = response.body()
                if (produceWrapper != null) {
                    Timber.i("Retrofit Delete ${produceWrapper.message}")
                    Timber.i("Retrofit Delete ${produceWrapper.data.toString()}")
                }
            }

            override fun onFailure(call: Call<ProduceWrapper>, t: Throwable) {
                Timber.i("Retrofit Delete Error : $t.message")
            }
        })
    }

    fun logAll() {
        Timber.v("** Produces List **")
        produces.forEach { Timber.v("Produce ${it}") }
    }
}