package com.example.final_project_android_admin.ui.viewmodel

import com.example.final_project_android_admin.request.LoginRequest
import com.example.final_project_android_admin.service.UserApi
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class LoginViewModelTest {
    private lateinit var service: UserApi

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getLoginResult():Unit = runBlocking {
        val respAuth = mockk<Response<AuthResponse>>()
        every {
            runBlocking {
                service.loginUser(LoginRequest("aa", "bb"))
            }
        } returns respAuth

        val result = service.loginUser(LoginRequest("aa", "bb"))
        runBlocking {
            service.loginUser(LoginRequest("aa", "bb"))
        }
        Assert.assertEquals(result, respAuth)
    }
}