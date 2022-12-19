package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.service.ApiHelper
import javax.inject.Inject

class AirportRepository (private val apiHelper: ApiHelper) {
    fun getAirport() = apiHelper.getAllAirport()
}