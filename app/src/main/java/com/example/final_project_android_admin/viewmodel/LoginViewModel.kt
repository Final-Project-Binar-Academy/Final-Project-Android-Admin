package com.example.final_project_android_admin.viewmodel

import androidx.lifecycle.*
import com.example.final_project_android_admin.data.api.request.LoginRequest
import com.example.final_project_android_admin.data.api.response.AuthResponse
import com.example.final_project_android_admin.data.api.response.BaseResponse
import com.example.final_project_android_admin.repository.UserRepository
import com.example.final_project_android_admin.utils.UserDataStoreManager
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepo: UserRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

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
    fun saveIsLoginStatus(status: Boolean) {
        viewModelScope.launch {
            pref.saveIsLoginStatus(status)
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }

    fun saveToken(token: String) {
        viewModelScope.launch {
            pref.saveToken(token)
        }
    }

    fun removeIsLoginStatus() {
        viewModelScope.launch {
            pref.removeIsLoginStatus()
        }
    }
    fun removeUsername() {
        viewModelScope.launch {
            pref.removeUsername()
        }
    }
    fun removeToken() {
        viewModelScope.launch {
            pref.removeToken()
        }
    }

    fun removeId() {
        viewModelScope.launch {
            pref.removeId()
        }
    }

    fun getDataStoreUsername(): LiveData<String> {
        return pref.getUsername.asLiveData()
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            pref.saveUsername(username)
        }
    }
}