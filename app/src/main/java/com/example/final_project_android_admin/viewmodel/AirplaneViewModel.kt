package com.example.final_project_android_admin.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.final_project_android_admin.data.api.request.AirplaneRequest
import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.response.BaseResponse
import com.example.final_project_android_admin.data.api.response.DeleteResponse
import com.example.final_project_android_admin.data.api.response.airplane.AirplaneIdResponse
import com.example.final_project_android_admin.data.api.response.airplane.AirplaneResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportIdResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.repository.AirplaneRepository
import com.example.final_project_android_admin.repository.AirportRepository
import com.example.final_project_android_admin.utils.UserDataStoreManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AirplaneViewModel(
    private val airplaneRepository: AirplaneRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

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

    val airplaneResult: MutableLiveData<BaseResponse<AirplaneResponse>> = MutableLiveData()

    fun createAirplane(airplane_name: String, airplane_code: String, company_id: Int, token: String) {
        airplaneResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val airplaneRequest = AirplaneRequest(
                    airplaneName = airplane_name,
                    airplaneCode = airplane_code,
                    companyId = company_id
                )
                val response = airplaneRepository.createAirplane(airplaneRequest = airplaneRequest, token)
                if (response?.code() == 201) {
                    airplaneResult.value = BaseResponse.Success(response.body())
                } else {
                    airplaneResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                airplaneResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }

    private val getDetailAirplane: MutableLiveData<AirplaneIdResponse?> = MutableLiveData()
    val airplaneDetail: LiveData<AirplaneIdResponse?> get() = getDetailAirplane

    fun getAirplaneDetail(id : Int){
        ApiClient.instance.getAirplaneDetail(id)
            .enqueue(object : Callback <AirplaneIdResponse> {
                override fun onResponse(
                    call: Call<AirplaneIdResponse>,
                    response: Response<AirplaneIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            airplaneRepository.getDetailAirplane(id)
                            getDetailAirplane.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<AirplaneIdResponse>, t: Throwable) {
                }
            })
    }

    fun updateAirplane(airplane_name: String, airplane_code: String, company_id: Int, token: String, id: Int) {
        ApiClient.instance.updateAirplane(AirplaneRequest(airplane_name, airplane_code, company_id), token, id)
            .enqueue(object : Callback<AirplaneIdResponse> {
                override fun onResponse(
                    call: Call<AirplaneIdResponse>,
                    response: Response<AirplaneIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            airplaneRepository.updateAirplane(AirplaneRequest(airplane_name, airplane_code, company_id), token, id)
                        }
                    }
                }

                override fun onFailure(call: Call<AirplaneIdResponse>, t: Throwable) {
                }
            })
    }

    fun deleteAirplane(token: String, id: Int) {
        ApiClient.instance.deleteAirplane(token, id)
            .enqueue(object : Callback<DeleteResponse> {
                override fun onResponse(
                    call: Call<DeleteResponse>,
                    response: Response<DeleteResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            airplaneRepository.deleteAirplane(token, id)
                        }
                    }
                }

                override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                }
            })
    }
}