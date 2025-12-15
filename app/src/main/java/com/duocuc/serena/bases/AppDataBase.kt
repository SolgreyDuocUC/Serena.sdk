package com.duocuc.serena.bases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.duocuc.serena.dao.RegistroEmocionalDao
import com.duocuc.serena.data.dataModel.EmotionalRegisterData

@Database(
    entities = [EmotionalRegisterData::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
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
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
            }
    }
}
