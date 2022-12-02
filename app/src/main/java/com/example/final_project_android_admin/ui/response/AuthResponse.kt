package com.example.final_project_android_admin.ui.response


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AuthResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)