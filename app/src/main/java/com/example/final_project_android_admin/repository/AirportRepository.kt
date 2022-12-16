package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.service.ApiHelperImpl


class AirportRepository(private val apiHelper: ApiHelperImpl) {
    fun getAirport() = apiHelper.getAirport()

}