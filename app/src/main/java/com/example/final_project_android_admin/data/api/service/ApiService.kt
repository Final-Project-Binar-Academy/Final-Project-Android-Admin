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
import retrofit2.http.*

interface ApiService {

    @POST("/api/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<AuthResponse>

    // airport
    @GET("/api/airport/")
    fun getAirport() : Call<AirportResponse>

    @GET("/api/airport/{id}")
    fun getAirportDetail(@Path("id") id: Int) : Call<AirportIdResponse>

    @POST("/api/airport/create")
    suspend fun createAirport(@Body airportRequest: AirportRequest, @Header("Authorization")token: String): Response<AirportResponse>

    @PUT("/api/airport/update/{id}")
    fun updateAirport(@Body airportRequest: AirportRequest, @Header("Authorization")token: String, @Path("id") id: Int): Call<AirportIdResponse>

    @DELETE("/api/airport/delete/{id}")
    fun deleteAirport(@Header("Authorization")token: String, @Path("id") id: Int): Call<DeleteResponse>


    // company
    @GET("/api/company")
    fun getCompany() : Call<CompanyResponse>

    @GET("/api/company/{id}")
    fun getCompanyDetail(@Path("id") id: Int) : Call<CompanyIdResponse>

    @Multipart
    @POST("/api/company/create")
    suspend fun createCompany(
        @Part("companyName") companyName: RequestBody,
        @Part image: MultipartBody.Part,
        @Header("Authorization")token: String): Response<CompanyResponse>

    @Multipart
    @PUT("/api/company/update/{id}")
    fun updateCompany(
        @Part("companyName") companyName: RequestBody,
        @Part image: MultipartBody.Part,
        @Header("Authorization")token: String,
        @Path("id") id: Int): Call<CompanyIdResponse>

    @DELETE("/api/company/delete/{id}")
    fun deleteCompany(@Header("Authorization")token: String, @Path("id") id: Int): Call<DeleteResponse>

//    airplane
    @GET("/api/airplane")
    fun getAirplane() : Call<AirplaneResponse>

    @GET("/api/airplane/{id}")
    fun getAirplaneDetail(@Path("id") id: Int) : Call<AirplaneIdResponse>

    @POST("/api/airplane/create")
    suspend fun createAirplane(@Body airplaneRequest: AirplaneRequest, @Header("Authorization")token: String): Response<AirplaneResponse>

    @PUT("/api/airplane/update/{id}")
    fun updateAirplane(@Body airplaneRequest: AirplaneRequest, @Header("Authorization")token: String, @Path("id") id: Int): Call<AirplaneIdResponse>

    @DELETE("/api/airplane/delete/{id}")
    fun deleteAirplane(@Header("Authorization")token: String, @Path("id") id: Int): Call<DeleteResponse>

//    ticket
    @GET("/api/ticket")
    fun getFlight() : Call<FlightResponse>

    @GET("/api/ticket/{id}")
    fun getFlightDetail(@Path("id") id: Int) : Call<FlightIdResponse>

    @POST("/api/ticket/create")
    suspend fun createFlight(@Body flightRequest: FlightRequest, @Header("Authorization")token: String): Response<FlightResponse>

    @PUT("/api/ticket/update/{id}")
    fun updateFlight(@Body flightRequest: FlightRequest, @Header("Authorization")token: String, @Path("id") id: Int): Call<FlightIdResponse>

    @DELETE("/api/ticket/delete/{id}")
    fun deleteFlight(@Header("Authorization")token: String, @Path("id") id: Int): Call<DeleteResponse>

//    transaction
    @GET("/api/transaction/admin/")
    fun getTransaction(@Header("Authorization")token: String): Call<TransactionResponse>

    @GET("/api/transaction/admin/{id}")
    fun getTransactionDetail(@Header("Authorization")token: String, @Path("id") id: Int): Call<TransactionIdResponse>

    @GET("/api/transaction/admin/filter")
    fun getTransactionFilter(@Header("Authorization")token: String, @Query("status")status: String): Call<TransactionResponse>

    @PUT("/api/transaction/admin/update/{id}")
    fun updateTransaction(@Body transactionRequest: TransactionRequest, @Header("Authorization")token: String, @Path("id") id: Int): Call<TransactionIdResponse>

    @DELETE("/api/transaction/admin/delete/{id}")
    fun deleteTransaction(@Header("Authorization")token: String, @Path("id") id: Int): Call<DeleteResponse>
}