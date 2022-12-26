package com.example.final_project_android_admin.data.api.response.transaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DataTransaction(
    @SerializedName("back")
    var back: Back?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("go")
    var go: Go?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("passenger")
    var passenger: Passenger?,
    @SerializedName("passengerId")
    var passengerId: Int?,
    @SerializedName("payment")
    var payment: Payment?,
    @SerializedName("paymentId")
    var paymentId: Int?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("ticketBack")
    var ticketBack: Int?,
    @SerializedName("ticketGo")
    var ticketGo: Int?,
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