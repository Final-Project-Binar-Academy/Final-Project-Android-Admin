package com.example.final_project_android_admin.data.api.response.transaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DataTransaction(
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("from")
    var from: From?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("passenger")
    var passenger: Passenger?,
    @SerializedName("passengerId")
    var passengerId: Int?,
    @SerializedName("paymentId")
    var paymentId: Any?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("ticketFrom")
    var ticketFrom: Int?,
    @SerializedName("ticketTo")
    var ticketTo: Int?,
    @SerializedName("to")
    var to: To?,
    @SerializedName("totalPrice")
    var totalPrice: String?,
    @SerializedName("transactionCode")
    var transactionCode: String?,
    @SerializedName("tripId")
    var tripId: Int?,
    @SerializedName("typeTrip")
    var typeTrip: TypeTrip?,
    @SerializedName("updatedAt")
    var updatedAt: String?,
    @SerializedName("user")
    var user: User?,
    @SerializedName("userId")
    var userId: Int?
)