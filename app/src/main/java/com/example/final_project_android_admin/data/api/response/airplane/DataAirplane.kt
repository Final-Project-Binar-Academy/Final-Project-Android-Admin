package com.example.final_project_android_admin.data.api.response.airplane


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DataAirplane(
    @SerializedName("airplaneCode")
    var airplaneCode: String?,
    @SerializedName("airplaneName")
    var airplaneName: String?,
    @SerializedName("company")
    var company: Company?,
    @SerializedName("companyeName")
    var companyeName: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)