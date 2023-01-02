package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.request.FlightRequest
import com.example.final_project_android_admin.data.api.service.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlightRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun getFlight() = apiHelper.getAllFlight()

    fun getDetailFlight(id: Int) = apiHelper.getDetailFlight(id)

    suspend fun createFlight(flightRequest: FlightRequest, token: String) =
        apiHelper.createFlight(flightRequest = flightRequest, token)

    fun updateFlight(flightRequest: FlightRequest, token: String, id: Int) =
        apiHelper.updateFlight(flightRequest = flightRequest, token, id)

    fun deleteFlight(token: String, id: Int) =
        apiHelper.deleteFlight(token, id)

}