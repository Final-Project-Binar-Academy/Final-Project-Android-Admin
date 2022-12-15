package com.example.final_project_android_admin.service

import com.example.final_project_android_admin.request.LoginRequest
import com.example.final_project_android_admin.response.AuthResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>?
}