package com.wit.farmersmarketredo.main

import android.app.Application

import timber.log.Timber

class FarmerApp : Application() {



    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Timber.i("Farmers Application Started")
    }
}