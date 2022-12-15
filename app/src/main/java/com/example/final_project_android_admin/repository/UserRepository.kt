package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.request.LoginRequest
import com.example.final_project_android_admin.service.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val apiHelper: ApiHelper) {
   suspend fun loginUser(loginRequest: LoginRequest) =
      apiHelper.loginUser(loginRequest = loginRequest)

}