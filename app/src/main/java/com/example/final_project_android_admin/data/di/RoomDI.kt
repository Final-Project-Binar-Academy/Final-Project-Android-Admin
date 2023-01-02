package com.example.final_project_android_admin.data.di

import android.content.Context
import androidx.room.Room
import com.example.final_project_android_admin.data.dao.CompanyDao
import com.example.final_project_android_admin.data.dao.CompanyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomDI {

    lateinit var database: CompanyDatabase

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CompanyDatabase{
        database =  Room.databaseBuilder(
            context,
            CompanyDatabase::class.java,
            CompanyDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

        return database
    }

    @Provides
    @Singleton
    fun provideUserDao(db: CompanyDatabase): CompanyDao {
        return db.companyDao()
    }
}