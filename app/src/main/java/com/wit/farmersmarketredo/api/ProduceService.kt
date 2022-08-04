package com.wit.farmersmarketredo.api


import com.wit.farmersmarketredo.models.ProduceModel
import retrofit2.Call
import retrofit2.http.*


interface ProduceService {
    @GET("/produces")
    fun getall(): Call<List<ProduceModel>>

    @GET("/produces/{id}")
    fun get(@Path("id") id: String): Call<ProduceModel>

    @DELETE("/produces/{id}")
    fun delete(@Path("id") id: String): Call<ProduceWrapper>

    @POST("/produces")
    fun post(@Body produce: ProduceModel): Call<ProduceWrapper>

    @PUT("/produces/{id}")
    fun put(@Path("id") id: String,
            @Body produce: ProduceModel
    ): Call<ProduceWrapper>
}