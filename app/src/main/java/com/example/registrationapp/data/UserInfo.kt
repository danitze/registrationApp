package com.example.registrationapp.data

import com.squareup.moshi.Json

data class UserInfo(
    @field:Json(name = "user_id") val userId: Int,
    @field:Json(name = "phone_code") val phoneCode: String,
    @field:Json(name = "phone_number") val phoneNumber: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "second_name") val surName: String
)
