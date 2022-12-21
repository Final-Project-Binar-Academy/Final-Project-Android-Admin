package com.example.final_project_android_admin.data.api.request


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AirplaneRequest(
    @SerializedName("airplaneCode")
    var airplaneCode: String?,
    @SerializedName("airplaneName")
    var airplaneName: String?,
    @SerializedName("companyId")
    var companyId: Int?
)