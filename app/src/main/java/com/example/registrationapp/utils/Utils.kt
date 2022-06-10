package com.example.registrationapp.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.registrationapp.data.ApiResult
import com.example.registrationapp.data.SignInRequest
import com.example.registrationapp.data.UserInfo
import com.example.registrationapp.states.ProfileFragmentState
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

fun <T> LifecycleOwner.observe(flow: Flow<T>, block: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(block)
        }
    }
}

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): ApiResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            ApiResult.Success(body)
        } else {
            ApiResult.Error(code = response.code(), msg = response.message())
        }
    } catch (e: HttpException) {
        ApiResult.Error(code = e.code(), msg = e.message())
    } catch (e: Throwable) {
        ApiResult.Exception(e)
    }
}

fun parseSignInRequest(fullPhoneNumber: String, password: String): SignInRequest? {
    return try {
        val numberProto = getFormattedPhoneNumber(fullPhoneNumber)
        SignInRequest(
            "+" + numberProto.countryCode,
            "" + numberProto.nationalNumber,
            password
        )
    } catch (e: Exception) {
        null
    }
}

fun getFormattedPhoneNumber(fullPhoneNumber: String): Phonenumber.PhoneNumber {
    val phoneUtil = PhoneNumberUtil.getInstance()
    return phoneUtil.parse(fullPhoneNumber, "")
}

fun toFullPhoneNumber(countryCode: String, number: String) = countryCode + number