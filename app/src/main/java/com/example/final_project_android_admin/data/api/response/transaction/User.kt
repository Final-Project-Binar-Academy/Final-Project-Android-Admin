package com.example.final_project_android_admin.data.api.response.transaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class User(
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("roleId")
    var roleId: Int?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)