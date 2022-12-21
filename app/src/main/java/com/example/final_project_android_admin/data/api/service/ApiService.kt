package com.example.final_project_android_admin.data.api.service

import com.example.final_project_android_admin.data.api.request.AirplaneRequest
import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.request.CompanyRequest
import com.example.final_project_android_admin.data.api.request.LoginRequest
import com.example.final_project_android_admin.data.api.response.AuthResponse
import com.example.final_project_android_admin.data.api.response.DeleteResponse
import com.example.final_project_android_admin.data.api.response.airplane.AirplaneIdResponse
import com.example.final_project_android_admin.data.api.response.airplane.AirplaneResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportIdResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import com.example.final_project_android_admin.data.api.response.company.CompanyIdResponse
import com.example.final_project_android_admin.data.api.response.company.CompanyResponse
import com.example.final_project_android_admin.data.api.response.flight.FlightResponse
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

    @POST("/api/company/create")
    suspend fun createCompany(@Body companyRequest: CompanyRequest, @Header("Authorization")token: String): Response<CompanyResponse>

    @PUT("/api/company/update/{id}")
    fun updateCompany(@Body companyRequest: CompanyRequest, @Header("Authorization")token: String, @Path("id") id: Int): Call<CompanyIdResponse>

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
}