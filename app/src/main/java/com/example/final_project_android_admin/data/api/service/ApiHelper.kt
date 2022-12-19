package com.example.final_project_android_admin.data.api.service

import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.request.LoginRequest
import com.example.final_project_android_admin.data.api.response.AuthResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import retrofit2.Response

class ApiHelper(private val apiService: ApiService) {

    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>? = apiService.loginUser(loginRequest = loginRequest)

    fun getAllAirport() = apiService.getAirport()

    suspend fun createAirport(airportRequest: AirportRequest): Response<AirportResponse>? = apiService.createAirport(airportRequest = airportRequest)

    fun getAllCompany() = apiService.getCompany()
}