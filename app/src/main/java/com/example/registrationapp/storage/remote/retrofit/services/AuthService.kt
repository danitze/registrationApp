package com.example.registrationapp.storage.remote.retrofit.services

import com.example.registrationapp.data.ApiResult
import com.example.registrationapp.data.SignInRequest
import com.example.registrationapp.data.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/login")
    suspend fun login(@Body signInRequest: SignInRequest): ApiResult<SignInResponse>
}