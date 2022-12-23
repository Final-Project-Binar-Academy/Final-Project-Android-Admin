package com.example.final_project_android_admin.data.api.response.airplane


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AirplaneIdResponse(
    @SerializedName("data")
    var `data`: DataAirplane?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)