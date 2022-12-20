package com.example.final_project_android_admin.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.repository.UserRepository
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.example.final_project_android_admin.viewmodel.LoginViewModel

class UserViewModelFactory(private val apiHelper: ApiHelper, private val pref: UserDataStoreManager) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(UserRepository(apiHelper), pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }
}