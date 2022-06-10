package com.example.registrationapp.events

sealed class ProfileEvent {
    data class NameChanged(val value: String) : ProfileEvent()
    data class SurnameChanged(val value: String) : ProfileEvent()
    data class PhoneNumberChanged(val value: String) : ProfileEvent()
    object SaveProfile : ProfileEvent()
}
