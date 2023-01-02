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
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService): ApiHelper {

    override suspend fun loginUser(loginRequest: LoginRequest): Response<AuthResponse> = apiService.loginUser(loginRequest = loginRequest)

    //    airport
    override fun getAllAirport() = apiService.getAirport()

    override fun getDetailAirport(id: Int): Call<AirportIdResponse> = apiService.getAirportDetail(id)

    override suspend fun createAirport(airportRequest: AirportRequest, token: String): Response<AirportResponse> = apiService.createAirport(airportRequest = airportRequest, token)

    override fun updateAirport(airportRequest: AirportRequest, token: String, id: Int): Call<AirportIdResponse> = apiService.updateAirport(airportRequest = airportRequest, token, id)

    override fun deleteAirport(token: String, id: Int): Call<DeleteResponse> = apiService.deleteAirport(token, id)

    //    company
    override fun getAllCompany() = apiService.getCompany()

    override fun getDetailCompany(id: Int): Call<CompanyIdResponse> = apiService.getCompanyDetail(id)

    override suspend fun createCompany(companyName: RequestBody, image: MultipartBody.Part, token: String): Response<CompanyResponse> = apiService.createCompany(companyName, image, token)

    override fun updateCompany(companyName: RequestBody, image: MultipartBody.Part, token: String, id: Int): Call<CompanyIdResponse> = apiService.updateCompany(companyName, image, token, id)

    override fun deleteCompany(token: String, id: Int): Call<DeleteResponse> = apiService.deleteCompany(token, id)


    //    airplane
    override fun getAllAirplane() = apiService.getAirplane()

    override fun getDetailAirplane(id: Int): Call<AirplaneIdResponse> = apiService.getAirplaneDetail(id)

    override suspend fun createAirplane(airplaneRequest: AirplaneRequest, token: String): Response<AirplaneResponse> = apiService.createAirplane(airplaneRequest = airplaneRequest, token)

    override fun updateAirplane(airplaneRequest: AirplaneRequest, token: String, id: Int): Call<AirplaneIdResponse> = apiService.updateAirplane(airplaneRequest = airplaneRequest, token, id)

    override fun deleteAirplane(token: String, id: Int): Call<DeleteResponse> = apiService.deleteAirplane(token, id)

    //    flight
    override fun getAllFlight() = apiService.getFlight()

    override fun getDetailFlight(id: Int): Call<FlightIdResponse> = apiService.getFlightDetail(id)

    override suspend fun createFlight(flightRequest: FlightRequest, token: String): Response<FlightResponse> = apiService.createFlight(flightRequest = flightRequest, token)

    override fun updateFlight(flightRequest: FlightRequest, token: String, id: Int): Call<FlightIdResponse> = apiService.updateFlight(flightRequest = flightRequest, token, id)

    override fun deleteFlight(token: String, id: Int): Call<DeleteResponse> = apiService.deleteFlight(token, id)

    //    transaction
    override fun getTransaction(token: String) = apiService.getTransaction(token)

    override fun getDetailTransaction(token: String, id: Int): Call<TransactionIdResponse> = apiService.getTransactionDetail(token, id)

    override fun getTransactionFilter(token: String, status: String) = apiService.getTransactionFilter(token, status)

    override fun updateTransaction(transactionRequest: TransactionRequest, token: String, id: Int): Call<TransactionIdResponse> = apiService.updateTransaction(transactionRequest, token, id)

    override fun deleteTransaction(token: String, id: Int): Call<DeleteResponse> = apiService.deleteTransaction(token, id)


}