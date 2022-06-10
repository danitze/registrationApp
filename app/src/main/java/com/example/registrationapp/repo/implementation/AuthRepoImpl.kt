package com.example.registrationapp.repo.implementation

import android.content.Context
import com.example.registrationapp.R
import com.example.registrationapp.data.ApiResult
import com.example.registrationapp.data.Result
import com.example.registrationapp.data.SignInResponse
import com.example.registrationapp.data.UserInfo
import com.example.registrationapp.repo.abstraction.AuthRepo
import com.example.registrationapp.storage.local.room.UserDao
import com.example.registrationapp.storage.remote.retrofit.services.AuthService
import com.example.registrationapp.utils.parseSignInRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepoImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authService: AuthService,
    private val userDao: UserDao
) : AuthRepo {
    override suspend fun signIn(fullPhoneNum: String, password: String): ApiResult<SignInResponse> {
        val signInRequest = parseSignInRequest(fullPhoneNum, password) ?: return ApiResult
            .Exception(IllegalArgumentException(context.getString(R.string.invalid_phone_error)))
        val result = authService.login(signInRequest)
        result.onSuccess {
            val newUser = it.userInfo
            if (userDao.getUserById(newUser.userId) != null) {
                userDao.updateUser(newUser)
            } else {
                userDao.insertUser(newUser)
            }
        }
        return result
    }

    override suspend fun getUserInfo(uid: Int): UserInfo? = userDao.getUserById(uid)

    override suspend fun updateUserInfo(userInfo: UserInfo): Result {
        return try {
            userDao.updateUser(userInfo)
            Result.Ok
        } catch (e: Exception) {
            Result.Fail(e)
        }
    }
}