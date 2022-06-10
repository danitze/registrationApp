package com.example.registrationapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "user")
data class UserInfo(
    @PrimaryKey
    @ColumnInfo(name = "uid")
    @field:Json(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "phone_code")
    @field:Json(name = "phone_code")
    val phoneCode: String,

    @ColumnInfo(name = "phone_number")
    @field:Json(name = "phone_number")
    val phoneNumber: String,

    @ColumnInfo(name = "name")
    @field:Json(name = "name")
    val name: String,

    @ColumnInfo(name = "surname")
    @field:Json(name = "second_name")
    val surName: String
)
