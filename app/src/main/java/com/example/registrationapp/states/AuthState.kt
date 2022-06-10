package com.example.registrationapp.states

sealed class AuthState {
    object Unauthorized : AuthState()
    data class Authorized(val userId: Int) : AuthState()
}
