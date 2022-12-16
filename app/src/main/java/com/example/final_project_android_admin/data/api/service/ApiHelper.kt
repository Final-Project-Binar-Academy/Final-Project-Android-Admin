package com.example.final_project_android_admin.data.api.service

import com.example.final_project_android_admin.data.api.request.LoginRequest
import com.example.final_project_android_admin.data.api.response.AuthResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>?
}