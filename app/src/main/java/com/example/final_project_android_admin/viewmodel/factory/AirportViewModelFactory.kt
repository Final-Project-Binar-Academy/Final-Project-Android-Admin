package com.example.final_project_android_admin.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.repository.AirportRepository
import com.example.final_project_android_admin.viewmodel.AirportViewModel

class AirportViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AirportViewModel::class.java)) {
            return AirportViewModel(AirportRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}