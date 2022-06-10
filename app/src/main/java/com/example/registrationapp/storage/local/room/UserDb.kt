package com.example.registrationapp.storage.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registrationapp.data.UserInfo

@Database(entities = [UserInfo::class], version = 1)
abstract class UserDb : RoomDatabase() {
    abstract fun userDao(): UserDao
}