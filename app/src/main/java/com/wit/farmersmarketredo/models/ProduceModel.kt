package com.wit.farmersmarketredo.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class ProduceModel(
    var uid: String? = "",
    var paymenttype: String = "N/A",
    var amount: Int = 0,
    var message: String = "Produce Details",
    var upvotes: Int = 0,
    var profilepic: String = "",
    var email: String? = "joe@bloggs.com",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0)
    : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "paymenttype" to paymenttype,
            "amount" to amount,
            "message" to message,
            "upvotes" to upvotes,
            "profilepic" to profilepic,
            "email" to email,
            "latitude" to latitude,
            "longitude" to longitude
        )
    }
}