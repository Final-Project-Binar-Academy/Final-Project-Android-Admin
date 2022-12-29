package com.example.final_project_android_admin.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.final_project_android_admin.repository.UserRepository
import com.example.final_project_android_admin.utils.TotalDataManager
import com.example.final_project_android_admin.utils.UserDataStoreManager
import kotlinx.coroutines.launch

class TotalDataViewModel(
    private val pref: TotalDataManager
) : ViewModel() {

    fun saveAirport(total: Int) {
        viewModelScope.launch {
            pref.saveAirport(total)
        }
    }
    fun saveAirplane(total: Int) {
        viewModelScope.launch {
            pref.saveAirplane(total)
        }
    }
    fun saveCompany(total: Int) {
        viewModelScope.launch {
            pref.saveCompany(total)
        }
    }
    fun savePayment(total: Int) {
        viewModelScope.launch {
            pref.savePayment(total)
        }
    }
    fun saveTicket(total: Int) {
        viewModelScope.launch {
            pref.saveTicket(total)
        }
    }
    fun getTotalAirport(): LiveData<Int> {
        return pref.getTotalAirport.asLiveData()
    }
    val getCompany: LiveData<Int>
        get() = getTotalCompany()

    fun getTotalAirplane(): LiveData<Int> {
        return pref.getTotalAirplane.asLiveData()
    }
    fun getTotalCompany(): LiveData<Int> {
        return pref.getTotalCompany.asLiveData()
    }
    fun getTotalPayment(): LiveData<Int> {
        return pref.getTotalCompany.asLiveData()
    }
    fun getTotalTicket(): LiveData<Int> {
        return pref.getTotalTicket.asLiveData()
    }

}