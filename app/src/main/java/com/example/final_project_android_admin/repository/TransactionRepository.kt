package com.example.final_project_android_admin.repository

import com.example.final_project_android_admin.data.api.request.FlightRequest
import com.example.final_project_android_admin.data.api.request.TransactionRequest
import com.example.final_project_android_admin.data.api.service.ApiHelper

class TransactionRepository (private val apiHelper: ApiHelper) {
    fun getTransaction(token: String) = apiHelper.getTransaction(token)

    fun getTransactionFilter(token: String, status: String) = apiHelper.getTransactionFilter(token, status)

    fun getDetailTransaction(token: String, id: Int) = apiHelper.getDetailTransaction(token, id)

    fun updateTransaction(transactionRequest: TransactionRequest, token: String, id: Int) =
        apiHelper.updateTransaction(transactionRequest, token, id)

}