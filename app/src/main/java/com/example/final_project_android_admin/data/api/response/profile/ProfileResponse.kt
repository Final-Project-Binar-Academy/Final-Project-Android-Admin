package com.binar.finalproject14.data.api.response.profile

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProfileResponse (
    @SerializedName("user")
    var `user`: User?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
    )
