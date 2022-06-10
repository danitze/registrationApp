package com.example.registrationapp.data

sealed class ApiResult<T> {
    class Success<T>(val data: T) : ApiResult<T>()
    class Error<T>(val code: Int, val msg: String?) : ApiResult<T>()
    class Exception<T>(val e: Throwable) : ApiResult<T>()

    inline fun onSuccess(block: (data: T) -> Unit): ApiResult<T> {
        if(this is Success) {
            block(data)
        }
        return this
    }

    inline fun onError(block: (code: Int, msg: String?) -> Unit): ApiResult<T> {
        if(this is Error) {
            block(code, msg)
        }
        return this
    }

    inline fun onException(block: (e: Throwable) -> Unit): ApiResult<T> {
        if(this is Exception) {
            block(e)
        }
        return this
    }
}
