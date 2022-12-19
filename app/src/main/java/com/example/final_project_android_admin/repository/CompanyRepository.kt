package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.service.ApiHelper

class CompanyRepository (private val apiHelper: ApiHelper) {
    fun getCompany() = apiHelper.getAllCompany()


}