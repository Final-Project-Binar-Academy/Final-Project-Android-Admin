package com.example.final_project_android_admin.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.example.final_project_android_admin.R
import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.response.BaseResponse
import com.example.final_project_android_admin.data.api.response.Data
import com.example.final_project_android_admin.data.api.response.DeleteResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportIdResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.response.airport.DataAirport
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.data.api.service.ApiService
import com.example.final_project_android_admin.repository.AirportRepository
import com.example.final_project_android_admin.utils.UserDataStoreManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val client: ApiService,
    private val airportRepository: AirportRepository,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {

    private val _airport: MutableLiveData<AirportResponse?> = MutableLiveData()
    fun getLiveDataAirport() : MutableLiveData<AirportResponse?> = _airport

    fun getDataAirport() {
        client.getAirport()
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

    private val _airportCity = MutableLiveData<List<DataAirport>?>()
    val LiveDataCityAirport: LiveData<List<DataAirport>?> = _airportCity

    fun getCityAirport(){
        client.getAirport().enqueue(object : Callback<AirportResponse> {
            override fun onResponse(
                call: Call<AirportResponse>,
                response: Response<AirportResponse>
            ) {
                if (response.isSuccessful) {
                    _airportCity.postValue(response.body()!!.data)
                }
            }

            override fun onFailure(call: Call<AirportResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
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
        client.getAirportDetail(id)
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

    fun updateAirport(airport_name: String, _city: String, _cityCode: String, token: String, id: Int) {
        client.updateAirport(AirportRequest(airport_name, _city, _cityCode), token, id)
            .enqueue(object : Callback<AirportIdResponse> {
                override fun onResponse(
                    call: Call<AirportIdResponse>,
                    response: Response<AirportIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            airportRepository.updateAirport(AirportRequest(airport_name, _city, _cityCode), token, id)
                        }
                    }
                }

                override fun onFailure(call: Call<AirportIdResponse>, t: Throwable) {
                }
            })
    }

    fun deleteAirport(token: String, id: Int) {
        client.deleteAirport(token, id)
            .enqueue(object : Callback<DeleteResponse> {
                override fun onResponse(
                    call: Call<DeleteResponse>,
                    response: Response<DeleteResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            airportRepository.deleteAirport(token, id)
                        }
                    }
                }

                override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                }
            })
    }

}
