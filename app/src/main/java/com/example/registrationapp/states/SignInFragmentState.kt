package com.example.registrationapp.states

data class SignInFragmentState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val password: String = ""
)