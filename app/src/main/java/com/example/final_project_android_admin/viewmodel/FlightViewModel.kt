package com.example.final_project_android_admin.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.final_project_android_admin.data.api.request.FlightRequest
import com.example.final_project_android_admin.data.api.response.BaseResponse
import com.example.final_project_android_admin.data.api.response.DeleteResponse
import com.example.final_project_android_admin.data.api.response.flight.DataFlight
import com.example.final_project_android_admin.data.api.response.flight.FlightIdResponse
import com.example.final_project_android_admin.data.api.response.flight.FlightResponse
import com.example.final_project_android_admin.data.api.service.ApiService
import com.example.final_project_android_admin.repository.FlightRepository
import com.example.final_project_android_admin.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FlightViewModel @Inject constructor(
    private val client: ApiService,
    private val flightRepository: FlightRepository,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {

    private val _flight: MutableLiveData<FlightResponse?> = MutableLiveData()
    fun getLiveDataFlight() : MutableLiveData<FlightResponse?> = _flight

    fun getDataFlight() {
        client.getFlight()
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

    val flightResult: MutableLiveData<BaseResponse<FlightResponse>> = MutableLiveData()

    fun createFlight(airplaneId: Int, arrivalDate: String,
                     arrivalTime: String, capacity: Int,
                     _class: String, code: String,
                     departureDate: String, departureTime: String,
                     flightFrom: Int, flightTo: Int,
                     price: Int, token: String) {
        flightResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val flightRequest = FlightRequest(
                    airplaneId, arrivalDate, arrivalTime, capacity, _class, code, departureDate,
                    departureTime, flightFrom, flightTo, price
                )
                val response = flightRepository.createFlight(flightRequest = flightRequest, token)
                if (response?.code() == 201) {
                    flightResult.value = BaseResponse.Success(response.body())
                } else {
                    flightResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                flightResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }

    private val _flightList = MutableLiveData<List<DataFlight>?>()
    val LiveDataListFlight: LiveData<List<DataFlight>?> = _flightList

    fun getListFlight(){
        client.getFlight().enqueue(object : Callback<FlightResponse> {
            override fun onResponse(
                call: Call<FlightResponse>,
                response: Response<FlightResponse>
            ) {
                if (response.isSuccessful) {
                    _flightList.postValue(response.body()!!.data)
                }
            }

            override fun onFailure(call: Call<FlightResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }
        })
    }


    private val getDetailFlight: MutableLiveData<FlightIdResponse?> = MutableLiveData()
    val flightDetail: LiveData<FlightIdResponse?> get() = getDetailFlight

    fun getFlightDetail(id : Int){
        client.getFlightDetail(id)
            .enqueue(object : Callback <FlightIdResponse> {
                override fun onResponse(
                    call: Call<FlightIdResponse>,
                    response: Response<FlightIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            flightRepository.getDetailFlight(id)
                            getDetailFlight.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<FlightIdResponse>, t: Throwable) {
                }
            })
    }

    fun updateFlight(airplaneId: Int, arrivalDate: String,
                     arrivalTime: String, capacity: Int,
                     _class: String, code: String,
                     departureDate: String, departureTime: String,
                     flightFrom: Int, flightTo: Int,
                     price: Int,
                     token: String, id: Int) {
        client.updateFlight(FlightRequest(
            airplaneId, arrivalDate, arrivalTime, capacity, _class, code, departureDate,
            departureTime, flightFrom, flightTo, price
        ), token, id)
            .enqueue(object : Callback<FlightIdResponse> {
                override fun onResponse(
                    call: Call<FlightIdResponse>,
                    response: Response<FlightIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            flightRepository.updateFlight(FlightRequest(
                                airplaneId, arrivalDate, arrivalTime, capacity, _class, code, departureDate,
                                departureTime, flightFrom, flightTo, price
                            ), token, id)
                        }
                    }
                }

                override fun onFailure(call: Call<FlightIdResponse>, t: Throwable) {
                }
            })
    }

    fun deleteFlight(token: String, id: Int) {
        client.deleteFlight(token, id)
            .enqueue(object : Callback<DeleteResponse> {
                override fun onResponse(
                    call: Call<DeleteResponse>,
                    response: Response<DeleteResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            flightRepository.deleteFlight(token, id)
                        }
                    }
                }

                override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                }
            })
    }

}