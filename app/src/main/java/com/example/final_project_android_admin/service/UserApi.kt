package com.example.final_project_android_admin.service

import com.example.final_project_android_admin.request.LoginRequest
import com.example.final_project_android_admin.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/api/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<AuthResponse>

}