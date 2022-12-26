package com.example.final_project_android_admin.data.api.response.transaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TypeTrip(
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)