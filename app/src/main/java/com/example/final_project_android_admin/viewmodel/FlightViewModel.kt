package com.example.final_project_android_admin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.final_project_android_admin.data.api.response.flight.FlightResponse
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.repository.FlightRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightViewModel (private val flightRepository: FlightRepository) : ViewModel() {

    private val _flight: MutableLiveData<FlightResponse?> = MutableLiveData()
    fun getLiveDataFlight() : MutableLiveData<FlightResponse?> = _flight

    fun getDataFlight() {
        ApiClient.instance.getFlight()
            .enqueue(object : Callback<FlightResponse> {
                override fun onResponse(
                    call: Call<FlightResponse>,
                    response: Response<FlightResponse>
                ) {
                    if (response.isSuccessful){
                        flightRepository.getFlight()
                        _flight.postValue(response.body())
                    }else{
                        _flight.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<FlightResponse>, t: Throwable) {
                    _flight.postValue(null)
                    Log.d("Failed",t.message.toString())
                }

            })
    }

}