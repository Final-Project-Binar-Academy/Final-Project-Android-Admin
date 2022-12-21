package com.example.final_project_android_admin.data.api.response.airport


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AirportIdResponse(
    @SerializedName("data")
    var `data`: DataAirport?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)