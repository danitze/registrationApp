package com.example.registrationapp.storage.remote.retrofit.adapter

import com.example.registrationapp.data.ApiResult
import com.example.registrationapp.utils.handleApi
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiResultCall<T : Any>(
    private val proxy: Call<T>
): Call<ApiResult<T>> {
    override fun enqueue(callback: Callback<ApiResult<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = runBlocking { handleApi { response } }
                callback.onResponse(this@ApiResultCall, Response.success(networkResult))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = ApiResult.Exception<T>(t)
                callback.onResponse(this@ApiResultCall, Response.success(networkResult))
            }

        })
    }

    override fun clone(): Call<ApiResult<T>> = ApiResultCall(proxy.clone())

    override fun execute(): Response<ApiResult<T>> = throw NotImplementedError()

    override fun isExecuted(): Boolean = proxy.isExecuted

    override fun cancel() = proxy.cancel()

    override fun isCanceled(): Boolean = proxy.isCanceled

    override fun request(): Request = proxy.request()

    override fun timeout(): Timeout = proxy.timeout()
}