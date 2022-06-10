package com.example.registrationapp.repo.implementation

import com.example.registrationapp.data.ApiResult
import com.example.registrationapp.data.SignInResponse
import com.example.registrationapp.repo.abstraction.AuthRepo
import com.example.registrationapp.storage.remote.retrofit.services.AuthService
import com.example.registrationapp.utils.parseSignInRequest
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepoImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepo {
    override suspend fun signIn(fullPhoneNum: String, password: String): ApiResult<SignInResponse> {
        val signInRequest = parseSignInRequest(fullPhoneNum, password) ?: return ApiResult
            .Exception(IllegalArgumentException("Incorrect phone number!"))
        val result = authService.login(signInRequest)
        result.onSuccess {
            //TODO save data to db
        }
        return result
    }
}