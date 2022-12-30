package com.binar.finalproject14.data.api.response.profile


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DataUser(
    @SerializedName("address")
    var address: String?,
    @SerializedName("avatar")
    var avatar: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("gender")
    var gender: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("phoneNumber")
    var phoneNumber: String?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)