package com.example.final_project_android_admin.data.api.response.transaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class To(
    @SerializedName("airplane")
    var airplane: Airplane?,
    @SerializedName("airplaneId")
    var airplaneId: Int?,
    @SerializedName("arrivalDate")
    var arrivalDate: String?,
    @SerializedName("arrivalTime")
    var arrivalTime: String?,
    @SerializedName("capacity")
    var capacity: Int?,
    @SerializedName("class")
    var classX: String?,
    @SerializedName("code")
    var code: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("departureDate")
    var departureDate: String?,
    @SerializedName("departureTime")
    var departureTime: String?,
    @SerializedName("destination")
    var destination: Destination?,
    @SerializedName("flightFrom")
    var flightFrom: Int?,
    @SerializedName("flightTo")
    var flightTo: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("origin")
    var origin: Origin?,
    @SerializedName("price")
    var price: Int?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)