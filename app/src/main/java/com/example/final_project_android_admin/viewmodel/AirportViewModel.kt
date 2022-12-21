package com.example.final_project_android_admin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.response.BaseResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.repository.AirportRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirportViewModel(private val airportRepository: AirportRepository) : ViewModel() {

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

}

// create
//    val airportResult: MutableLiveData<BaseResponse<AirportResponse>> = MutableLiveData()

//    fun createAirport(_airportName: Editable?, _city: Editable?, _cityCode: Editable?) {
//        airportResult.value = BaseResponse.Loading()
//        viewModelScope.launch {
//            try {
//                val airportRequest = AirportRequest(
//                    airportName = _airportName,
//                    city = _city,
//                    cityCode = _cityCode
//                )
//                val response = repository.createAirport(airportRequest = airportRequest)
//                if (response.code() == 201) {
//                    airportResult.value = BaseResponse.Success(response.body())
//                } else {
//                    airportResult.value = BaseResponse.Error(response.message())
//                }
//
//            } catch (ex: Exception) {
//                airportResult.value = BaseResponse.Error(ex.message)
//            }
//        }
//    }
