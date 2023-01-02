package com.example.final_project_android_admin.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.final_project_android_admin.data.api.response.company.DataCompany

@Database(entities = [DataCompany::class], version = 1)
abstract class CompanyDatabase : RoomDatabase() {
    abstract fun companyDao() : CompanyDao

    companion object {
        const val DATABASE_NAME = "myDatabase"

        var INSTANCE: CompanyDatabase? = null

        fun getAppDataBase(context: Context): CompanyDatabase {
            if (INSTANCE == null){
                synchronized(CompanyDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CompanyDatabase::class.java, DATABASE_NAME).build()
                }
            }
            return INSTANCE!!
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}