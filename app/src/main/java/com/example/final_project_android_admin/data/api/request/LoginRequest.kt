package com.example.final_project_android_admin.data.api.request


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LoginRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("password")
    var password: String?
)