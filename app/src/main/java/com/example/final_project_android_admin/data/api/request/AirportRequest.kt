package com.example.final_project_android_admin.data.api.request


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AirportRequest(
    @SerializedName("airportName")
    var airportName: String?,
    @SerializedName("city")
    var city: String?,
    @SerializedName("cityCode")
    var cityCode: String?
)