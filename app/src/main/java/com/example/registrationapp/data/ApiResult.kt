package com.example.registrationapp.data

@Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
sealed class ApiResult<T> {
    class Success<T>(val data: T) : ApiResult<T>()
    class Error<T>(val code: Int, val msg: String?) : ApiResult<T>()
    class Exception<T>(val e: Throwable) : ApiResult<T>()

    inline fun onSuccess(block: (data: T) -> Unit) {
        if(this is Success) {
            block(data)
        }
    }

    suspend inline fun onAsyncSuccess(block: suspend (data: T) -> Unit) {
        if(this is Success) {
            block(data)
        }
    }

    inline fun onError(block: (code: Int, msg: String?) -> Unit) {
        if(this is Error) {
            block(code, msg)
        }
    }

    suspend inline fun onAsyncError(block: suspend (code: Int, msg: String?) -> Unit) {
        if(this is Error) {
            block(code, msg)
        }
    }

    inline fun onException(block: (e: Throwable) -> Unit) {
        if(this is Exception) {
            block(e)
        }
    }

    suspend inline fun onAsyncException(block: suspend (e: Throwable) -> Unit) {
        if(this is Exception) {
            block(e)
        }
    }
}
