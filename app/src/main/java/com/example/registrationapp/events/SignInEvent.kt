package com.example.registrationapp.events

sealed class SignInEvent {
    data class PhoneNumberChanged(val value: String) : SignInEvent()
    data class PasswordChanged(val value: String) : SignInEvent()
    object SignIn : SignInEvent()
}
