package com.example.final_project_android_admin.data.api.response.company


import com.google.gson.annotations.SerializedName
import android.support.annotation.Keep

@Keep
data class Data(
    @SerializedName("companyImage")
    var companyImage: String?,
    @SerializedName("companyName")
    var companyName: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)