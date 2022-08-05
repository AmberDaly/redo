package com.wit.farmersmarketredo.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ProduceClient {
    val serviceURL = "mongodb+srv://Amber:<LLEPXB9e7>@cluster0.ssnasco.mongodb.net/?retryWrites=true&w=majority"

    fun getApi() : ProduceService {

        val gson = GsonBuilder().create()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val apiInterface = Retrofit.Builder()
            .baseUrl(serviceURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        return apiInterface.create(ProduceService::class.java)
    }
}