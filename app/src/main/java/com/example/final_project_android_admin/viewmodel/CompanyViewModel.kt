package com.example.final_project_android_admin.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.final_project_android_admin.data.api.request.AirportRequest
import com.example.final_project_android_admin.data.api.request.CompanyRequest
import com.example.final_project_android_admin.data.api.response.BaseResponse
import com.example.final_project_android_admin.data.api.response.DeleteResponse
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.response.company.CompanyIdResponse
import com.example.final_project_android_admin.data.api.response.company.CompanyResponse
import com.example.final_project_android_admin.data.api.response.company.DataCompany
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.repository.CompanyRepository
import com.example.final_project_android_admin.utils.UserDataStoreManager
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyViewModel(private val companyRepository: CompanyRepository,
private val pref: UserDataStoreManager
) : ViewModel() {

    private val _company: MutableLiveData<CompanyResponse?> = MutableLiveData()
    fun getLiveDataCompany() : MutableLiveData<CompanyResponse?> = _company

    fun getDataCompany() {
        ApiClient.instance.getCompany()
            .enqueue(object : Callback<CompanyResponse> {
                override fun onResponse(
                    call: Call<CompanyResponse>,
                    response: Response<CompanyResponse>
                ) {
                    if (response.isSuccessful){
                        companyRepository.getCompany()
                        _company.postValue(response.body())
                    }else{
                        _company.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
                    _company.postValue(null)
                    Log.d("Failed",t.message.toString())
                }

            })
    }

    private val _airplaneCompany = MutableLiveData<List<DataCompany>?>()
    val LiveDataAirplaneCompany: LiveData<List<DataCompany>?> = _airplaneCompany

    fun getAirplaneCompany(){
        ApiClient.instance.getCompany().enqueue(object : Callback<CompanyResponse> {
            override fun onResponse(
                call: Call<CompanyResponse>,
                response: Response<CompanyResponse>
            ) {
                if (response.isSuccessful) {
                    _airplaneCompany.postValue(response.body()!!.data)
                }
            }

            override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
                Log.e("Error : ", "onFailure: ${t.message}")
            }
        })
    }

    val companyResult: MutableLiveData<BaseResponse<CompanyResponse>> = MutableLiveData()

    fun createCompany(companyName: RequestBody, image: MultipartBody.Part, token: String) {
        companyResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {
                val response = companyRepository.createCompany(companyName, image, token)
                if (response?.code() == 201) {
                    companyResult.value = BaseResponse.Success(response.body())
                } else {
                    companyResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                companyResult.value = BaseResponse.Error(ex.message)
            }
        }
    }

    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }

    private val getDetailCompany: MutableLiveData<CompanyIdResponse?> = MutableLiveData()
    val companyDetail: LiveData<CompanyIdResponse?> get() = getDetailCompany

    fun getCompanyDetail(id : Int){
        ApiClient.instance.getCompanyDetail(id)
            .enqueue(object : Callback <CompanyIdResponse> {
                override fun onResponse(
                    call: Call<CompanyIdResponse>,
                    response: Response<CompanyIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            companyRepository.getDetailCompany(id)
                            getDetailCompany.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<CompanyIdResponse>, t: Throwable) {
                }
            })
    }

    fun updateCompany(companyName: RequestBody, image: MultipartBody.Part, token: String, id: Int) {
        ApiClient.instance.updateCompany(companyName, image, token, id)
            .enqueue(object : Callback<CompanyIdResponse> {
                override fun onResponse(
                    call: Call<CompanyIdResponse>,
                    response: Response<CompanyIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            companyRepository.updateCompany(companyName, image, token, id)
                        }
                    }
                }

                override fun onFailure(call: Call<CompanyIdResponse>, t: Throwable) {
                }
            })
    }

    fun deleteCompany(token: String, id: Int) {
        ApiClient.instance.deleteCompany(token, id)
            .enqueue(object : Callback<DeleteResponse> {
                override fun onResponse(
                    call: Call<DeleteResponse>,
                    response: Response<DeleteResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            companyRepository.deleteCompany(token, id)
                        }
                    }
                }

                override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                }
            })
    }

}