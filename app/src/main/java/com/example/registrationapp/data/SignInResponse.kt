package com.example.registrationapp.data

import com.squareup.moshi.Json

data class SignInResponse(
    @field:Json(name = "status") val status: String,
    @field:Json(name = "user") val userInfo: UserInfo
)
