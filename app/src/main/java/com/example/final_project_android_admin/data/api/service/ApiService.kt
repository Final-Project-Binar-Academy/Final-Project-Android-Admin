package com.example.final_project_android_admin.data.api.service

import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.request.LoginRequest
import com.example.final_project_android_admin.data.api.response.AuthResponse
import com.example.final_project_android_admin.data.api.response.airplane.AirplaneResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.response.company.CompanyResponse
import com.example.final_project_android_admin.data.api.response.flight.FlightResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("/api/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<AuthResponse>

    @GET("/api/airport/")
    fun getAirport() : Call<AirportResponse>

    @POST("/api/airport/create")
    suspend fun createAirport(@Body airportRequest: AirportRequest, @Header("Authorization")token: String): Response<AirportResponse>

    @GET("/api/company")
    fun getCompany() : Call<CompanyResponse>

    @GET("/api/airplane")
    fun getAirplane() : Call<AirplaneResponse>

    @GET("/api/ticket")
    fun getFlight() : Call<FlightResponse>
}