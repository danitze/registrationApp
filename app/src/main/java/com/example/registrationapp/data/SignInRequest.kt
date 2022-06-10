package com.example.registrationapp.data

import com.squareup.moshi.Json

data class SignInRequest(
    @field:Json(name = "phone_code") val phoneCode: String,
    @field:Json(name = "phone_number") val phoneNumber: String,
    @field:Json(name = "password") val password: String
)