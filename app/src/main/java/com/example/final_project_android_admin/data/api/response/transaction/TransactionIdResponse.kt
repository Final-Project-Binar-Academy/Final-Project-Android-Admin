package com.example.final_project_android_admin.data.api.response.transaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionIdResponse(
    @SerializedName("data")
    var `data`: DataTransaction?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)