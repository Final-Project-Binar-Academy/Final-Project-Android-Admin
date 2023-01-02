package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.request.AirplaneRequest
import com.example.final_project_android_admin.data.api.service.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AirplaneRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun getAirplane() = apiHelper.getAllAirplane()

    fun getDetailAirplane(id: Int) = apiHelper.getDetailAirplane(id)

    suspend fun createAirplane(airplaneRequest: AirplaneRequest, token: String) =
        apiHelper.createAirplane(airplaneRequest = airplaneRequest, token)

    fun updateAirplane(airplaneRequest: AirplaneRequest, token: String, id: Int) =
        apiHelper.updateAirplane(airplaneRequest = airplaneRequest, token, id)

    fun deleteAirplane(token: String, id: Int) =
        apiHelper.deleteAirplane(token, id)

}