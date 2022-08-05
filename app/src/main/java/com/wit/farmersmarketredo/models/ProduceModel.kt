package com.wit.farmersmarketredo.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProduceModel(var id: Long = 0,
                        val paymenttype: String = "N/A",
                        val amount: Int = 0,
                        val message: String = "n/a") : Parcelable