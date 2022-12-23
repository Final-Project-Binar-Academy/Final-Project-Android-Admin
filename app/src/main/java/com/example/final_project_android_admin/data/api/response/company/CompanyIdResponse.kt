package com.example.final_project_android_admin.data.api.response.company


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CompanyIdResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)