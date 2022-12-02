package com.example.final_project_android_admin.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.final_project_android_admin.ui.request.LoginRequest
import com.example.final_project_android_admin.ui.response.BaseResponse
import com.example.final_project_android_admin.ui.response.AuthResponse
import com.example.final_project_android_admin.ui.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    application: Application,
    private val userRepo: UserRepository
) : AndroidViewModel(application) {

//    val userRepo = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<AuthResponse>> = MutableLiveData()

    fun loginUser(email: String, pwd: String) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(
                    password = pwd,
                    email = email
                )
                val response = userRepo.loginUser(loginRequest = loginRequest)
                if (response?.code() == 200) {
                    loginResult.value = BaseResponse.Success(response.body())
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}