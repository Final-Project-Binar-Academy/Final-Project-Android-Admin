package com.example.final_project_android_admin.data.api.service

import com.example.final_project_android_admin.data.api.request.*
import com.example.final_project_android_admin.data.api.response.AuthResponse
import com.example.final_project_android_admin.data.api.response.DeleteResponse
import com.example.final_project_android_admin.data.api.response.airplane.AirplaneIdResponse
import com.example.final_project_android_admin.data.api.response.airplane.AirplaneResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportIdResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.response.company.CompanyIdResponse
import com.example.final_project_android_admin.data.api.response.company.CompanyResponse
import com.example.final_project_android_admin.data.api.response.flight.FlightIdResponse
import com.example.final_project_android_admin.data.api.response.flight.FlightResponse
import com.example.final_project_android_admin.data.api.response.transaction.TransactionIdResponse
import com.example.final_project_android_admin.data.api.response.transaction.TransactionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

interface ApiHelper {

    suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse>

//    airport
    fun getAllAirport(): Call<AirportResponse>

    fun getDetailAirport(id: Int): Call<AirportIdResponse>

    suspend fun createAirport(airportRequest: AirportRequest, token: String): Response<AirportResponse>

    fun updateAirport(airportRequest: AirportRequest, token: String, id: Int): Call<AirportIdResponse>

    fun deleteAirport(token: String, id: Int): Call<DeleteResponse>

//    company
    fun getAllCompany(): Call<CompanyResponse>
    fun getDetailCompany(id: Int): Call<CompanyIdResponse>
    suspend fun createCompany(companyName: RequestBody, image: MultipartBody.Part, token: String): Response<CompanyResponse>
    fun updateCompany(companyName: RequestBody, image: MultipartBody.Part, token: String, id: Int): Call<CompanyIdResponse>
    fun deleteCompany(token: String, id: Int): Call<DeleteResponse>

    //    airplane
    fun getAllAirplane(): Call<AirplaneResponse>
    fun getDetailAirplane(id: Int): Call<AirplaneIdResponse>
    suspend fun createAirplane(airplaneRequest: AirplaneRequest, token: String): Response<AirplaneResponse>
    fun updateAirplane(airplaneRequest: AirplaneRequest, token: String, id: Int): Call<AirplaneIdResponse>
    fun deleteAirplane(token: String, id: Int): Call<DeleteResponse>
//    flight
    fun getAllFlight(): Call<FlightResponse>
    fun getDetailFlight(id: Int): Call<FlightIdResponse>
    suspend fun createFlight(flightRequest: FlightRequest, token: String): Response<FlightResponse>
    fun updateFlight(flightRequest: FlightRequest, token: String, id: Int): Call<FlightIdResponse>
    fun deleteFlight(token: String, id: Int): Call<DeleteResponse>
//    transaction
    fun getTransaction(token: String): Call<TransactionResponse>
    fun getDetailTransaction(token: String, id: Int): Call<TransactionIdResponse>
    fun getTransactionFilter(token: String, status: String): Call<TransactionResponse>
    fun updateTransaction(transactionRequest: TransactionRequest , token: String, id: Int): Call<TransactionIdResponse>
    fun deleteTransaction(token: String, id: Int): Call<DeleteResponse>

}