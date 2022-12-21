package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.service.ApiHelper

class AirportRepository (private val apiHelper: ApiHelper) {
    fun getAirport() = apiHelper.getAllAirport()

    fun getDetailAirport(id: Int) = apiHelper.getDetailAirport(id)

    suspend fun createAirport(airportRequest: AirportRequest, token: String) =
        apiHelper.createAirport(airportRequest = airportRequest, token)

    fun updateAirport(airportRequest: AirportRequest, token: String, id: Int) =
        apiHelper.updateAirport(airportRequest = airportRequest, token, id)

    fun deleteAirport(token: String, id: Int) =
        apiHelper.deleteAirport(token, id)

}