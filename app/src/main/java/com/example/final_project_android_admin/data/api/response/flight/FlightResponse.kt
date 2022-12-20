package com.example.final_project_android_admin.data.api.response.flight


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FlightResponse(
    @SerializedName("data")
    var `data`: List<DataFlight?>?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalData")
    var totalData: Int?
)