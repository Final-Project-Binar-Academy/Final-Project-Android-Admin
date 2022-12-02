package com.example.final_project_android_admin.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.final_project_android_admin.ui.request.RegisterRequest
import com.example.final_project_android_admin.ui.response.AuthResponse
import com.example.final_project_android_admin.ui.response.BaseResponse
import com.example.final_project_android_admin.ui.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    val userRepo = UserRepository()
    val registerResult: MutableLiveData<BaseResponse<AuthResponse>> = MutableLiveData()

    fun registerUser(fname: String, lname: String, email: String, pwd: String) {
        registerResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val registerRequest = RegisterRequest(
                    firstName = fname,
                    lastName = lname,
                    password = pwd,
                    email = email
                )
                val response = userRepo.registerUser(registerRequest = registerRequest)
                if (response?.code() == 201) {
                    registerResult.value = BaseResponse.Success(response.body())
                } else {
                    registerResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                registerResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}