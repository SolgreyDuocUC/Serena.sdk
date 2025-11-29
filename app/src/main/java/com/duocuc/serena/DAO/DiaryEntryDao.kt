package com.duocuc.serena.dao

import androidx.room.Dao
import androidx.room.Insert
import com.duocuc.serena.model.DiaryEntry

@Dao
interface DiaryEntryDao {
    @Insert
    suspend fun insert(entry: DiaryEntry)
}
