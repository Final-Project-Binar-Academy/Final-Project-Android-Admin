package com.example.final_project_android_admin.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.response.BaseResponse
import com.example.final_project_android_admin.data.api.response.Data
import com.example.final_project_android_admin.data.api.response.airport.AirportIdResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiService
import com.example.final_project_android_admin.repository.AirportRepository
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirportViewModel(
    private val airportRepository: AirportRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

    private val _airport: MutableLiveData<AirportResponse?> = MutableLiveData()
    fun getLiveDataAirport() : MutableLiveData<AirportResponse?> = _airport

    fun getDataAirport() {
        ApiClient.instance.getAirport()
            .enqueue(object : Callback<AirportResponse> {
                override fun onResponse(
                    call: Call<AirportResponse>,
                    response: Response<AirportResponse>
                ) {
                    if (response.isSuccessful){
                        airportRepository.getAirport()
                        _airport.postValue(response.body())
                    }else{
                        _airport.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<AirportResponse>, t: Throwable) {
                    _airport.postValue(null)
                    Log.d("Failed",t.message.toString())
                }

            })
    }

    val airportResult: MutableLiveData<BaseResponse<AirportResponse>> = MutableLiveData()

    fun createAirport(airport_name: String, _city: String, _cityCode: String, token: String) {
        airportResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val airportRequest = AirportRequest(
                    airportName = airport_name,
                    city = _city,
                    cityCode = _cityCode
                )
                val response = airportRepository.createAirport(airportRequest = airportRequest, token)
                if (response?.code() == 201) {
                    airportResult.value = BaseResponse.Success(response.body())
                } else {
                    airportResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                airportResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }

    private val getDetailAirport: MutableLiveData<AirportIdResponse?> = MutableLiveData()
    val airportDetail: LiveData<AirportIdResponse?> get() = getDetailAirport

    fun getAirportDetail(id : Int){
        ApiClient.instance.getAirportDetail(id)
            .enqueue(object : Callback <AirportIdResponse> {
                override fun onResponse(
                    call: Call<AirportIdResponse>,
                    response: Response<AirportIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            airportRepository.getDetailAirport(id)
                            getDetailAirport.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<AirportIdResponse>, t: Throwable) {
                }
            })
    }


    private val airportUpdate: MutableLiveData<AirportIdResponse?> = MutableLiveData()

    fun updateAirport(airport_name: String, _city: String, _cityCode: String, token: String, id: Int) {
        ApiClient.instance.updateAirport(AirportRequest(airport_name, _city, _cityCode), token, id)
            .enqueue(object : Callback<AirportIdResponse> {
                override fun onResponse(
                    call: Call<AirportIdResponse>,
                    response: Response<AirportIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            airportRepository.updateAirport(AirportRequest(airport_name, _city, _cityCode), token, id)
                            airportUpdate.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<AirportIdResponse>, t: Throwable) {
                }
            })
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

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }

}
