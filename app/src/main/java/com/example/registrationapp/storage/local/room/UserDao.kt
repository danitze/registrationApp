package com.example.registrationapp.storage.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.registrationapp.data.UserInfo

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE uid LIKE :uid")
    suspend fun getUserById(uid: Int): UserInfo?

    @Update
    suspend fun updateUser(user: UserInfo)

    @Insert
    suspend fun insertUser(user: UserInfo)

}