package com.example.final_project_android_admin.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.repository.AirplaneRepository
import com.example.final_project_android_admin.repository.AirportRepository
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.AirplaneViewModel
import com.example.final_project_android_admin.viewmodel.AirportViewModel

class AirplaneViewModelFactory (private val apiHelper: ApiHelper, private val pref: UserDataStoreManager) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AirplaneViewModel::class.java)) {
            return AirplaneViewModel(AirplaneRepository(apiHelper), pref) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}