package com.example.registrationapp.repo.abstraction

import com.example.registrationapp.data.ApiResult
import com.example.registrationapp.data.Result
import com.example.registrationapp.data.SignInResponse
import com.example.registrationapp.data.UserInfo

interface AuthRepo {
    suspend fun signIn(fullPhoneNum: String, password: String): ApiResult<SignInResponse>
    suspend fun getUserInfo(uid: Int): UserInfo?
    suspend fun updateUserInfo(userInfo: UserInfo): Result
}