package com.example.final_project_android_admin.data.api.request


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class FlightRequest(
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
    @SerializedName("departureDate")
    var departureDate: String?,
    @SerializedName("departureTime")
    var departureTime: String?,
    @SerializedName("flightFrom")
    var flightFrom: Int?,
    @SerializedName("flightTo")
    var flightTo: Int?,
    @SerializedName("price")
    var price: Int?,
    @SerializedName("seatNumber")
    var seatNumber: String?
)