package com.example.final_project_android_admin.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.final_project_android_admin.data.api.response.company.CompanyResponse
import com.example.final_project_android_admin.data.api.response.company.DataCompany
import com.example.final_project_android_admin.data.api.service.ApiHelper
import com.example.final_project_android_admin.data.api.service.ApiService
import com.example.final_project_android_admin.data.dao.CompanyDao
import com.example.final_project_android_admin.data.dao.CompanyDatabase
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyRepository @Inject constructor(private val client: ApiService,
                                            private val apiHelper: ApiHelper,
                                            application: Application
) {

    val companyDao = CompanyDatabase.getAppDataBase(application).companyDao()

    var listDataCompany = MutableLiveData<List<DataCompany>?>()

    fun insert(item: DataCompany) {
//        Log.d("State", "Inserting Data")
        InsertItemAsyncTask(
            companyDao
        ).execute(item)
    }

    private fun deleteAllDataCompanys() {
        DeleteAllItemAsyncTask(
            companyDao
        ).execute()
    }

    fun getAllDataCompanys(): LiveData<List<DataCompany>> {
        return companyDao.getAllCompany()
    }

    private class InsertItemAsyncTask(val companyDao: CompanyDao) : AsyncTask<DataCompany, Unit, Unit>() {
        override fun doInBackground(vararg item: DataCompany?) {
            companyDao.insertCompany(item[0]!!)
            Log.d("State", "Inserting Data")
        }
    }

    private class DeleteAllItemAsyncTask(val companyDao: CompanyDao) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg p0: Unit?) {
            companyDao.deleteAllCompany()
        }
    }


    fun fetchDataCompany(context: Context?) {
//        deleteAllDataCompanys()
        client.getCompany()
            .enqueue(object : Callback<CompanyResponse> {
                override fun onResponse(
                    call: Call<CompanyResponse>,
                    response: Response<CompanyResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            listDataCompany.postValue(responseBody.data)
                            val listData = responseBody.data
                            listData?.let {
                                for (item in it)
                                {
                                    //inserting item to sqlite
                                    insert(item)
                                }
                            }
                        } else {
                            Toast.makeText(context, "error_fetch_companys", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "error_fetch_companys", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
                    Toast.makeText(context, "error_fetch_companys", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getCompany() = apiHelper.getAllCompany()

    fun getDetailCompany(id: Int) = apiHelper.getDetailCompany(id)

    suspend fun createCompany(companyName: RequestBody, image: MultipartBody.Part, token: String) =
        apiHelper.createCompany(companyName, image, token)

    fun updateCompany(companyName: RequestBody, image: MultipartBody.Part, token: String, id: Int) =
        apiHelper.updateCompany(companyName, image, token, id)

    fun deleteCompany(token: String, id: Int) {
        apiHelper.deleteCompany(token, id)
        deleteAllDataCompanys()
    }

}