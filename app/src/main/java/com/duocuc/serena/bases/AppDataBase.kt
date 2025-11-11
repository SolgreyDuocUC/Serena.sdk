package com.duocuc.serena.bases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duocuc.serena.dao.RegistroEmocionalDao
import com.duocuc.serena.dao.UserDAO
import com.duocuc.serena.dao.UserSessionDao
import com.duocuc.serena.data.EmotionalRegisterData
import com.duocuc.serena.data.UserActiveSession
import com.duocuc.serena.data.UserData

@Database(
    entities = [
        UserData::class,
        EmotionalRegisterData::class,
        UserActiveSession::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun registroEmocionalDao(): RegistroEmocionalDao
    abstract fun userSessionDao(): UserSessionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "serena.db"
                )
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
