package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.request.AirplaneRequest
import com.example.final_project_android_admin.data.api.request.CompanyRequest
import com.example.final_project_android_admin.data.api.service.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart

class CompanyRepository (private val apiHelper: ApiHelper) {
    fun getCompany() = apiHelper.getAllCompany()

    fun getDetailCompany(id: Int) = apiHelper.getDetailCompany(id)

    suspend fun createCompany(companyName: RequestBody, image: MultipartBody.Part, token: String) =
        apiHelper.createCompany(companyName, image, token)

    fun updateCompany(companyName: RequestBody, image: MultipartBody.Part, token: String, id: Int) =
        apiHelper.updateCompany(companyName, image, token, id)

    fun deleteCompany(token: String, id: Int) =
        apiHelper.deleteCompany(token, id)

}