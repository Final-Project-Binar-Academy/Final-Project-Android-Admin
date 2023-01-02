package com.example.final_project_android_admin.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.final_project_android_admin.data.api.response.company.DataCompany

@Dao
interface CompanyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompany(company: DataCompany)

    @Update
    fun updateCompany(company: DataCompany)

    @Query("DELETE FROM tb_company")
    fun deleteAllCompany()

    @Query("SELECT * FROM tb_company")
    fun getAllCompanys(): LiveData<List<DataCompany>>
}