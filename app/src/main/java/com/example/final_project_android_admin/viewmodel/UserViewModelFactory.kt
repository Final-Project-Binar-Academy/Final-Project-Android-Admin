package com.example.final_project_android_admin.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.repository.UserRepository

class UserViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    private lateinit var application: Application
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(UserRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }
}