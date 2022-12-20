package com.example.final_project_android_admin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.final_project_android_admin.data.api.response.airplane.AirplaneResponse
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.repository.AirplaneRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirplaneViewModel (private val airplaneRepository: AirplaneRepository) : ViewModel() {

    private val _airplane: MutableLiveData<AirplaneResponse?> = MutableLiveData()
    fun getLiveDataAirplane() : MutableLiveData<AirplaneResponse?> = _airplane

    fun getDataAirplane() {
        ApiClient.instance.getAirplane()
            .enqueue(object : Callback<AirplaneResponse> {
                override fun onResponse(
                    call: Call<AirplaneResponse>,
                    response: Response<AirplaneResponse>
                ) {
                    if (response.isSuccessful){
                        airplaneRepository.getAirplane()
                        _airplane.postValue(response.body())
                    }else{
                        _airplane.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<AirplaneResponse>, t: Throwable) {
                    _airplane.postValue(null)
                    Log.d("Failed",t.message.toString())
                }

            })
    }

}