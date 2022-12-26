package com.example.final_project_android_admin.data.api.response.airplane


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AirplaneResponse(
    @SerializedName("data")
    var `data`: List<DataAirplane>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalData")
    var totalData: Int?
)