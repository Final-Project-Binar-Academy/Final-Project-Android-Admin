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
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    fun getDetailCompany(id: Int): Call<CompanyIdResponse> = apiService.getCompanyDetail(id)

    suspend fun createCompany(companyName: RequestBody, image: MultipartBody.Part, token: String): Response<CompanyResponse> = apiService.createCompany(companyName, image, token)

    fun updateCompany(companyName: RequestBody, image: MultipartBody.Part, token: String, id: Int): Call<CompanyIdResponse> = apiService.updateCompany(companyName, image, token, id)

    fun deleteCompany(token: String, id: Int): Call<DeleteResponse> = apiService.deleteCompany(token, id)


    //    airplane
    fun getAllAirplane() = apiService.getAirplane()

    fun getDetailAirplane(id: Int): Call<AirplaneIdResponse> = apiService.getAirplaneDetail(id)

    suspend fun createAirplane(airplaneRequest: AirplaneRequest, token: String): Response<AirplaneResponse> = apiService.createAirplane(airplaneRequest = airplaneRequest, token)

    fun updateAirplane(airplaneRequest: AirplaneRequest, token: String, id: Int): Call<AirplaneIdResponse> = apiService.updateAirplane(airplaneRequest = airplaneRequest, token, id)

    fun deleteAirplane(token: String, id: Int): Call<DeleteResponse> = apiService.deleteAirplane(token, id)

//    flight
    fun getAllFlight() = apiService.getFlight()

    fun getDetailFlight(id: Int): Call<FlightIdResponse> = apiService.getFlightDetail(id)

    suspend fun createFlight(flightRequest: FlightRequest, token: String): Response<FlightResponse> = apiService.createFlight(flightRequest = flightRequest, token)

    fun updateFlight(flightRequest: FlightRequest, token: String, id: Int): Call<FlightIdResponse> = apiService.updateFlight(flightRequest = flightRequest, token, id)

    fun deleteFlight(token: String, id: Int): Call<DeleteResponse> = apiService.deleteFlight(token, id)

//    transaction
    fun getTransaction(token: String) = apiService.getTransaction(token)

    fun getDetailTransaction(token: String, id: Int): Call<TransactionIdResponse> = apiService.getTransactionDetail(token, id)

    fun getTransactionFilter(token: String, status: String) = apiService.getTransactionFilter(token, status)

    fun updateTransaction(transactionRequest: TransactionRequest , token: String, id: Int): Call<TransactionIdResponse> = apiService.updateTransaction(transactionRequest, token, id)

    fun deleteTransaction(token: String, id: Int): Call<DeleteResponse> = apiService.deleteTransaction(token, id)


}