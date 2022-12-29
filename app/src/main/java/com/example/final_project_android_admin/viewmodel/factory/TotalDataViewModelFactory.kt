package com.example.final_project_android_admin.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.repository.TransactionRepository
import com.example.final_project_android_admin.utils.TotalDataManager
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.TotalDataViewModel
import com.example.final_project_android_admin.viewmodel.TransactionViewModel

class TotalDataViewModelFactory (private val pref: TotalDataManager) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TotalDataViewModel::class.java)) {
            return TotalDataViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}