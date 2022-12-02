package com.example.final_project_android_admin.ui.service

import com.example.final_project_android_admin.ui.request.LoginRequest
import com.example.final_project_android_admin.ui.response.AuthResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>?
}