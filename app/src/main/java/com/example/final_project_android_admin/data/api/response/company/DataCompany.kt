package com.example.final_project_android_admin.data.api.response.company


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tb_company")
data class DataCompany(
    @Expose
    @SerializedName("companyImage")
    var companyImage: String?,

    @Expose
    @SerializedName("companyName")
    var companyName: String?,

    @Expose
    @SerializedName("createdAt")
    var createdAt: String?,

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int?,

    @Expose
    @SerializedName("updatedAt")
    var updatedAt: String?
)