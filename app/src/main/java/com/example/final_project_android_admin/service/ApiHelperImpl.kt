package com.example.final_project_android_admin.service

import com.example.final_project_android_admin.request.LoginRequest
import com.example.final_project_android_admin.response.AuthResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: UserApi): ApiHelper {
    override suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>? = apiService.loginUser(loginRequest = loginRequest)
}