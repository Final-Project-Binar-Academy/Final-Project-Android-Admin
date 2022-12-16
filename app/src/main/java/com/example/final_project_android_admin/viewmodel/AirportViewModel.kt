package com.example.final_project_android_admin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.repository.AirportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val repository: AirportRepository
    ) : ViewModel() {

    private val _airport: MutableLiveData<AirportResponse?> = MutableLiveData()
    fun getLiveDataAirport() : MutableLiveData<AirportResponse?> = _airport

    fun getDataAirport() {
        repository.getAirport()
            .enqueue(object : Callback<AirportResponse> {
                override fun onResponse(
                    call: Call<AirportResponse>,
                    response: Response<AirportResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _airport.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<AirportResponse>, t: Throwable) {
                    _airport.postValue(null)
                    Log.d("Failed",t.message.toString())
                }
            })
    }
}
