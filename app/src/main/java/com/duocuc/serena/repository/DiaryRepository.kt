package com.duocuc.serena.repository

import com.duocuc.serena.dao.DiaryEntryDao
import com.duocuc.serena.model.DiaryEntry
import java.time.LocalDate

class DiaryRepository(private val diaryEntryDao: DiaryEntryDao) {

    suspend fun saveDiaryEntry(title: String, content: String, date: LocalDate) {
        val newEntry = DiaryEntry(title = title, content = content, date = date)
        diaryEntryDao.insert(newEntry)
    }
}