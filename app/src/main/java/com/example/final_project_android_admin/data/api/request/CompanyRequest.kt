package com.example.final_project_android_admin.data.api.request


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CompanyRequest(
    @SerializedName("companyImage")
    var companyImage: String?,
    @SerializedName("companyName")
    var companyName: String?
)