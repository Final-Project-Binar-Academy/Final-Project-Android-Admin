package com.example.final_project_android_admin.data.api.response


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("email")
    var email: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("role")
    var role: String?,
    @SerializedName("roleId")
    var roleId: Int?,
    @SerializedName("accessToken")
    var accessToken: String?
)