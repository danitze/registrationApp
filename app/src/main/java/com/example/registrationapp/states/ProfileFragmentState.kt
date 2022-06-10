package com.example.registrationapp.states

data class ProfileFragmentState(
    val isLoading: Boolean = false,
    val dataChanged: Boolean = false,
    val name: String = "",
    val surname: String = "",
    val phoneNumber: String = "",
)
