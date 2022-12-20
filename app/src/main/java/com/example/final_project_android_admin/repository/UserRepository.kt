package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.request.LoginRequest
import com.example.final_project_android_admin.data.api.service.ApiHelper

class UserRepository (private val apiHelper: ApiHelper) {
   suspend fun loginUser(loginRequest: LoginRequest) =
      apiHelper.loginUser(loginRequest = loginRequest)

}