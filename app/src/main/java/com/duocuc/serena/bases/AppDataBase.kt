package com.duocuc.serena.bases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.duocuc.serena.dao.RegistroEmocionalDao
import com.duocuc.serena.dao.UserDAO
import com.duocuc.serena.dao.UserSessionDao
import com.duocuc.serena.data.dataModel.UserData
import com.duocuc.serena.data.dataModel.UserActiveSession
import com.duocuc.serena.data.dataModel.EmotionalRegisterData

@Database(
    entities = [UserData::class, UserActiveSession::class, EmotionalRegisterData::class],
    version = 3, // Incrementar la versión por el cambio de esquema
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun userSessionDao(): UserSessionDao
    abstract fun registroEmocionalDao(): RegistroEmocionalDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "serena.db"
                )
                .fallbackToDestructiveMigration() // Opcional: para evitar crashes en desarrollo
                .build().also { INSTANCE = it }
            }
    }
}
