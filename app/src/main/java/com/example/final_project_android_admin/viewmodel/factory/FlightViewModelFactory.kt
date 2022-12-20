package com.example.final_project_android_admin.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.repository.FlightRepository
import com.example.final_project_android_admin.viewmodel.FlightViewModel

class FlightViewModelFactory (private val apiHelper: ApiHelper) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightViewModel::class.java)) {
            return FlightViewModel(FlightRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}