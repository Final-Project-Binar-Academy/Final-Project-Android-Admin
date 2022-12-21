package com.example.final_project_android_admin.data.api.service

import com.example.final_project_android_admin.data.api.request.AirplaneRequest
import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.request.LoginRequest
import com.example.final_project_android_admin.data.api.response.AuthResponse
import com.example.final_project_android_admin.data.api.response.DeleteResponse
import com.example.final_project_android_admin.data.api.response.airplane.AirplaneIdResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportIdResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import retrofit2.Call
import retrofit2.Response

class ApiHelper(private val apiService: ApiService) {

    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse> = apiService.loginUser(loginRequest = loginRequest)

//    airport
    fun getAllAirport() = apiService.getAirport()

    fun getDetailAirport(id: Int): Call<AirportIdResponse> = apiService.getAirportDetail(id)

    suspend fun createAirport(airportRequest: AirportRequest, token: String): Response<AirportResponse> = apiService.createAirport(airportRequest = airportRequest, token)

    fun updateAirport(airportRequest: AirportRequest, token: String, id: Int): Call<AirportIdResponse> = apiService.updateAirport(airportRequest = airportRequest, token, id)

    fun deleteAirport(token: String, id: Int): Call<DeleteResponse> = apiService.deleteAirport(token, id)

//    company
    fun getAllCompany() = apiService.getCompany()

//    airplane
    fun getAllAirplane() = apiService.getAirplane()

    fun getDetailAirplane(id: Int): Call<AirplaneIdResponse> = apiService.getAirplaneDetail(id)

    fun updateAirplane(airplaneRequest: AirplaneRequest, token: String, id: Int): Call<AirplaneIdResponse> = apiService.updateAirplane(airplaneRequest = airplaneRequest, token, id)

    fun deleteAirplane(token: String, id: Int): Call<DeleteResponse> = apiService.deleteAirplane(token, id)

//    flight
    fun getAllFlight() = apiService.getFlight()
}