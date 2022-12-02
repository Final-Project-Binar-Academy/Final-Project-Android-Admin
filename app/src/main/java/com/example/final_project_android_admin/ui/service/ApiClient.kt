package com.example.final_project_android_admin.ui.service

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiClient {

    val BASE_URL = "https://lef-id.up.railway.app"

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): UserApi {
        return retrofit.create()
    }

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper {
        return apiHelper
    }
}
//object ApiClient {
//    const val BASE_URL = "https://lef-id.up.railway.app"
//
//    var mHttpLoggingInterceptor = HttpLoggingInterceptor()
//        .setLevel(HttpLoggingInterceptor.Level.BODY)
//
//    var mOkHttpClient = OkHttpClient
//        .Builder()
//        .addInterceptor(mHttpLoggingInterceptor)
//        .build()
//
//    var mRetrofit: Retrofit? = null
//
//
//    val client: Retrofit?
//        get() {
//            if(mRetrofit == null){
//                mRetrofit = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .client(mOkHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            }
//            return mRetrofit
//        }
//}