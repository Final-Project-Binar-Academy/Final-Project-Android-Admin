package com.example.final_project_android_admin.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.final_project_android_admin.data.api.response.BaseResponse
import com.example.final_project_android_admin.data.api.response.DeleteResponse
import com.example.final_project_android_admin.data.api.response.company.CompanyIdResponse
import com.example.final_project_android_admin.data.api.response.company.CompanyResponse
import com.example.final_project_android_admin.data.api.response.company.DataCompany
import com.example.final_project_android_admin.data.api.service.ApiService
import com.example.final_project_android_admin.repository.CompanyRepository
import com.example.final_project_android_admin.utils.UserDataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val client: ApiService,
    private val companyRepository: CompanyRepository,
    private val pref: UserDataStoreManager,
    application: Application
) : AndroidViewModel(application) {

    fun fetchCompanyData(context: Context?) = companyRepository.fetchDataCompany(context)
    fun getCompanyData() = companyRepository.getAllDataCompanys()


    private val _airplaneCompany = MutableLiveData<List<DataCompany>?>()
    val LiveDataAirplaneCompany: LiveData<List<DataCompany>?> = _airplaneCompany

    fun getAirplaneCompany(){
        client.getCompany().enqueue(object : Callback<CompanyResponse> {
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
        client.getCompanyDetail(id)
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
        client.updateCompany(companyName, image, token, id)
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
        client.deleteCompany(token, id)
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