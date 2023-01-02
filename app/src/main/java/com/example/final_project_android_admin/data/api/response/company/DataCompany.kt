package com.example.final_project_android_admin.data.api.response.company


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "tb_company")
class DataCompany: Serializable {
    @Expose
    @SerializedName("companyImage")
    var companyImage: String? = null

    @Expose
    @SerializedName("companyName")
    var companyName: String? = null

    @Expose
    @SerializedName("createdAt")
    var createdAt: String? = null

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int? = null

    @Expose
    @SerializedName("updatedAt")
    var updatedAt: String? = null
}