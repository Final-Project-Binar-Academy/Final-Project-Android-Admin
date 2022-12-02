package com.example.final_project_android_admin.ui.repository

import com.example.final_project_android_admin.ui.service.UserApi
import com.example.final_project_android_admin.ui.request.LoginRequest
import com.example.final_project_android_admin.ui.request.RegisterRequest
import com.example.final_project_android_admin.ui.response.AuthResponse
import retrofit2.Response

class UserRepository {

   suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>? {
      return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
   }

    suspend fun registerUser(registerRequest: RegisterRequest): Response<AuthResponse>? {
        return  UserApi.getApi()?.registerUser(registerRequest = registerRequest)
    }
}