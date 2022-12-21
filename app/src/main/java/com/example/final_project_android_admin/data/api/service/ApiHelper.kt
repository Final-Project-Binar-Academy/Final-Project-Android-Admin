package com.example.final_project_android_admin.data.api.service

import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.request.LoginRequest
import com.example.final_project_android_admin.data.api.response.AuthResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportIdResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import retrofit2.Call
import retrofit2.Response

class ApiHelper(private val apiService: ApiService) {

    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse> = apiService.loginUser(loginRequest = loginRequest)

    fun getAllAirport() = apiService.getAirport()

    fun getDetailAirport(id: Int): Call<AirportIdResponse> = apiService.getAirportDetail(id)

    suspend fun createAirport(airportRequest: AirportRequest, token: String): Response<AirportResponse> = apiService.createAirport(airportRequest = airportRequest, token)

    fun updateAirport(airportRequest: AirportRequest, token: String, id: Int): Call<AirportIdResponse> = apiService.updateAirport(airportRequest = airportRequest, token, id)

    fun getAllCompany() = apiService.getCompany()

    fun getAllAirplane() = apiService.getAirplane()

    fun getAllFlight() = apiService.getFlight()
}