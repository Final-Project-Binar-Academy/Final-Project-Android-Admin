package com.example.final_project_android_admin.data.api.service

import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.request.LoginRequest
import com.example.final_project_android_admin.data.api.response.AuthResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService): ApiHelper {
    override suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse> = apiService.loginUser(loginRequest = loginRequest)

    override suspend fun createAirport(airportRequest: AirportRequest): Response<AirportResponse> = apiService.createAirport(airportRequest = airportRequest)

    fun getAirport() = apiService.getAirport()

}