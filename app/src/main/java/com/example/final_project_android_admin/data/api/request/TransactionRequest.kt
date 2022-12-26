package com.example.final_project_android_admin.data.api.request


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionRequest(
    @SerializedName("brithDate")
    var brithDate: String?,
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("NIK")
    var nIK: String?,
    @SerializedName("ticketBack")
    var ticketBack: Int?,
    @SerializedName("ticketGo")
    var ticketGo: Int?,
    @SerializedName("tripId")
    var tripId: Int?
)