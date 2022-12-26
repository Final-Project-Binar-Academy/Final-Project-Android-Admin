package com.example.final_project_android_admin.data.api.response.transaction


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Payment(
    @SerializedName("accountName")
    var accountName: String?,
    @SerializedName("accountNumber")
    var accountNumber: String?,
    @SerializedName("bankLogo")
    var bankLogo: String?,
    @SerializedName("bankName")
    var bankName: String?,
    @SerializedName("createdAt")
    var createdAt: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("updatedAt")
    var updatedAt: String?
)