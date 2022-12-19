package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.service.ApiHelper

class AirportRepository (private val apiHelper: ApiHelper) {
    fun getAirport() = apiHelper.getAllAirport()

    suspend fun createAirport(airportRequest: AirportRequest) =
        apiHelper.createAirport(airportRequest = airportRequest)

}