package com.example.final_project_android_admin.ui.request

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class RegisterRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("password")
    var password: String?
)