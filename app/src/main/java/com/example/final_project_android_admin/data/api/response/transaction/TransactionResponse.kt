package com.example.final_project_android_admin.data.api.response.transaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionResponse(
    @SerializedName("data")
    var `data`: List<DataTransaction>,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalData")
    var totalData: Int?
)