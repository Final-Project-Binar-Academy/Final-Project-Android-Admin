package com.example.final_project_android_admin.data.api.response.flight


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Destination(
    @SerializedName("airportName")
    var airportName: String?,
    @SerializedName("city")
    var city: String?,
    @SerializedName("cityCode")
    var cityCode: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)