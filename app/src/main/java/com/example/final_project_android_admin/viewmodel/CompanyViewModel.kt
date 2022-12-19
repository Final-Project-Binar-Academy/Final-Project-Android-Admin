package com.example.final_project_android_admin.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.final_project_android_admin.data.api.response.airport.AirportResponse
import com.example.final_project_android_admin.data.api.response.company.CompanyResponse
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.repository.CompanyRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyViewModel(private val companyRepository: CompanyRepository ) : ViewModel() {

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

}