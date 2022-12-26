package com.example.final_project_android_admin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.final_project_android_admin.data.api.request.TransactionRequest
import com.example.final_project_android_admin.data.api.response.DeleteResponse
import com.example.final_project_android_admin.data.api.response.transaction.TransactionIdResponse
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

    private val _transactionFilter: MutableLiveData<TransactionResponse?> = MutableLiveData()
    fun getTransactionFilter(): MutableLiveData<TransactionResponse?> = _transactionFilter

    fun getDataTransactionFilter(token: String, status: String) {
        ApiClient.instance.getTransactionFilter(token, status)
            .enqueue(object : Callback<TransactionResponse> {
                override fun onResponse(
                    call: Call<TransactionResponse>,
                    response: Response<TransactionResponse>
                ) {
                    if (response.isSuccessful) {
                        transactionRepository.getTransactionFilter(token, status)
                        _transactionFilter.postValue(response.body())
                    } else {
                        _transactionFilter.postValue(null)
                        Log.d("notSuccess", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                    _transactionFilter.postValue(null)
                    Log.d("Failed", t.message.toString())
                }

            })
    }

    private val getDetailTransaction: MutableLiveData<TransactionIdResponse?> = MutableLiveData()
    val transactionDetail: LiveData<TransactionIdResponse?> get() = getDetailTransaction

    fun getTransactionDetail(token: String, id : Int){
        ApiClient.instance.getTransactionDetail(token, id)
            .enqueue(object : Callback <TransactionIdResponse> {
                override fun onResponse(
                    call: Call<TransactionIdResponse>,
                    response: Response<TransactionIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            transactionRepository.getDetailTransaction(token, id)
                            getDetailTransaction.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<TransactionIdResponse>, t: Throwable) {
                }
            })
    }

    fun updateTransaction(birthDate: String, firstname: String,
                        lastname: String, _nik: String, ticketBack: Int,
                        ticketGo: Int, tripId: Int, token: String, id: Int) {
        ApiClient.instance.updateTransaction(
            TransactionRequest(birthDate, firstname, lastname, _nik, ticketBack, ticketGo, tripId), token, id)
            .enqueue(object : Callback<TransactionIdResponse> {
                override fun onResponse(
                    call: Call<TransactionIdResponse>,
                    response: Response<TransactionIdResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            transactionRepository.updateTransaction(
                                TransactionRequest(birthDate, firstname, lastname, _nik, ticketBack, ticketGo, tripId
                            ), token, id)
                        }
                    }
                }

                override fun onFailure(call: Call<TransactionIdResponse>, t: Throwable) {
                }
            })
    }

    fun deleteTransaction(token: String, id: Int) {
        ApiClient.instance.deleteTransaction(token, id)
            .enqueue(object : Callback<DeleteResponse> {
                override fun onResponse(
                    call: Call<DeleteResponse>,
                    response: Response<DeleteResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            transactionRepository.deleteTransaction(token, id)
                        }
                    }
                }

                override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                }
            })
    }
}