package com.example.registrationapp.repo.abstraction

import com.example.registrationapp.data.ApiResult
import com.example.registrationapp.data.SignInResponse

interface AuthRepo {
    suspend fun signIn(fullPhoneNum: String, password: String): ApiResult<SignInResponse>
}