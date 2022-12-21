package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.request.AirplaneRequest
import com.example.final_project_android_admin.data.api.request.CompanyRequest
import com.example.final_project_android_admin.data.api.service.ApiHelper

class CompanyRepository (private val apiHelper: ApiHelper) {
    fun getCompany() = apiHelper.getAllCompany()

    fun getDetailCompany(id: Int) = apiHelper.getDetailCompany(id)

//    suspend fun createCompany(companyRequest: CompanyRequest, token: String) =
//        apiHelper.createCompany(companyRequest = companyRequest, token)

    fun updateCompany(companyRequest: CompanyRequest, token: String, id: Int) =
        apiHelper.updateCompany(companyRequest = companyRequest, token, id)

    fun deleteCompany(token: String, id: Int) =
        apiHelper.deleteCompany(token, id)

}