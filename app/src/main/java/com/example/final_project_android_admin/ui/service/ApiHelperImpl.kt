package com.example.final_project_android_admin.ui.service

import com.example.final_project_android_admin.ui.request.LoginRequest
import com.example.final_project_android_admin.ui.response.AuthResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: UserApi): ApiHelper {
    override suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>? = apiService.loginUser(loginRequest = loginRequest)
}