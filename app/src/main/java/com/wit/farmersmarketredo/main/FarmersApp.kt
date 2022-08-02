package com.wit.farmersmarketredo.main

import android.app.Application
import timber.log.Timber
import com.wit.farmersmarketredo.models.ProduceMemStore
import com.wit.farmersmarketredo.models.ProduceStore

class FarmersApp : Application() {

    lateinit var producesStore: ProduceStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        producesStore = ProduceMemStore()
        Timber.i("Starting FarmersApp Application")
    }
}