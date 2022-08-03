package com.wit.farmersmarketredo.main

import android.app.Application
import timber.log.Timber
import com.wit.farmersmarketredo.models.ProduceManager
import com.wit.farmersmarketredo.models.ProduceStore

class FarmersApp : Application() {

    lateinit var producesStore: ProduceStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
      //  producesStore = ProduceManager()
        Timber.i("Starting FarmersApp Application")
    }
}