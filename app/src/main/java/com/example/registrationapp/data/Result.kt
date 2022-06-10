package com.example.registrationapp.data

sealed class Result {
    object Ok : Result()
    data class Fail(val e: Throwable) : Result()

    inline fun onOk(block: () -> Unit): Result {
        if(this is Ok) {
            block()
        }
        return this
    }

    inline fun onFail(block: (e: Throwable) -> Unit): Result {
        if(this is Fail) {
            block(e)
        }
        return this
    }
}
