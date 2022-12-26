package com.example.final_project_android_admin.data.api.response.transaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Passenger(
    @SerializedName("brithDate")
    var brithDate: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("NIK")
    var nIK: String?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)