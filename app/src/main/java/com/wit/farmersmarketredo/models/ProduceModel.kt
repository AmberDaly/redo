package com.wit.farmersmarketredo.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProduceModel(var _id: String = "N/A",
                        @SerializedName("paymenttype")
                        val paymenttype: String = "N/A",
                        val message: String = "n/a",
                        val amount: Int = 0) : Parcelable