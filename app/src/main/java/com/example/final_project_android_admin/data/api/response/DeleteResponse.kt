package com.example.final_project_android_admin.data.api.response


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeleteResponse(
    @SerializedName("message")
    var message: String?,
    @SerializedName("status")
    var status: String?
)