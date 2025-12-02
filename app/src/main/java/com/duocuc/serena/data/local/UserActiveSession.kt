package com.duocuc.serena.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_active_session")
data class UserActiveSession(
    @PrimaryKey val id: Int = 1,
    val activeUserId: Long
)