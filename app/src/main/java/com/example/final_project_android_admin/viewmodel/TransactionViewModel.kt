package com.example.final_project_android_admin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.final_project_android_admin.data.api.response.transaction.TransactionResponse
import com.example.final_project_android_admin.data.api.service.ApiClient
import com.example.final_project_android_admin.repository.TransactionRepository
import com.example.final_project_android_admin.utils.UserDataStoreManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionViewModel (
    private val transactionRepository: TransactionRepository,
    private val pref: UserDataStoreManager
) : ViewModel() {

    private val _transaction: MutableLiveData<TransactionResponse?> = MutableLiveData()
    fun getLiveDataTransaction(): MutableLiveData<TransactionResponse?> = _transaction

    fun getDataTransaction(token: String) {
        ApiClient.instance.getTransaction(token)
            .enqueue(object : Callback<TransactionResponse> {
                override fun onResponse(
                    call: Call<TransactionResponse>,
                    response: Response<TransactionResponse>
                ) {
                    if (response.isSuccessful) {
                        transactionRepository.getTransaction(token)
                        _transaction.postValue(response.body())
                    } else {
                        _transaction.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                    _transaction.postValue(null)
                    Log.d("Failed", t.message.toString())
                }

            })
    }

    fun getDataStoreToken(): LiveData<String> {
        return pref.getToken.asLiveData()
    }
}