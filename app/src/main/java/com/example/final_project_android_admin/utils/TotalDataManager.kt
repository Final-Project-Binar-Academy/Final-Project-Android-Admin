package com.example.final_project_android_admin.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TotalDataManager(@ApplicationContext val context: Context) {
    companion object {
        private const val DATASTORE_NAME = "data_preferences"
        private val TOTAL_AIRPORT = intPreferencesKey("TOTAL_AIRPORT")
        private val TOTAL_AIRPLANE = intPreferencesKey("TOTAL_AIRPLANE")
        private val TOTAL_COMPANY = intPreferencesKey("TOTAL_COMPANY")
        private val TOTAL_PAYMENT = intPreferencesKey("TOTAL_PAYMENT")
        private val TOTAL_TICKET = intPreferencesKey("TOTAL_TICKET")

        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )
    }

    val getTotalAirport: Flow<Int> = context.dataStore.data.map {
        it[TOTAL_AIRPORT] ?: 0
    }

    val getTotalAirplane: Flow<Int> = context.dataStore.data.map {
        it[TOTAL_AIRPLANE] ?: 0
    }

    val getTotalCompany: Flow<Int> = context.dataStore.data.map {
        it[TOTAL_COMPANY] ?: 0
    }

    val getTotalPayment: Flow<Int> = context.dataStore.data.map {
        it[TOTAL_PAYMENT] ?: 0
    }

    val getTotalTicket: Flow<Int> = context.dataStore.data.map {
        it[TOTAL_TICKET] ?: 0
    }

    suspend fun saveAirport(total: Int) {
        context.dataStore.edit {
            it[TOTAL_AIRPORT] = total
        }
    }

    suspend fun saveAirplane(total: Int) {
        context.dataStore.edit {
            it[TOTAL_AIRPLANE] = total
        }
    }

    suspend fun saveCompany(total: Int) {
        context.dataStore.edit {
            it[TOTAL_COMPANY] = total
        }
    }

    suspend fun savePayment(total: Int) {
        context.dataStore.edit {
            it[TOTAL_PAYMENT] = total
        }
    }

    suspend fun saveTicket(total: Int) {
        context.dataStore.edit {
            it[TOTAL_TICKET] = total
        }
    }
}