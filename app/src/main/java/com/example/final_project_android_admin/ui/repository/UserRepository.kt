package com.example.final_project_android_admin.ui.repository

import com.example.final_project_android_admin.ui.service.UserApi
import com.example.final_project_android_admin.ui.request.LoginRequest
import com.example.final_project_android_admin.ui.response.AuthResponse
import com.example.final_project_android_admin.ui.service.ApiHelper
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val apiHelper: ApiHelper) {
   suspend fun loginUser(loginRequest: LoginRequest) =
      apiHelper.loginUser(loginRequest = loginRequest)

}