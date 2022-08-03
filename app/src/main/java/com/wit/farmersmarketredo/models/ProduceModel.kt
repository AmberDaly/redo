package com.wit.farmersmarketredo.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProduceModel(var _id: String = "N/A",
                         val paymentmethod: String = "N/A",
                        val message: String = "n/a",
                         val amount: Int = 0) : Parcelable