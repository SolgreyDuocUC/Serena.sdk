package com.duocuc.serena.dao

import androidx.room.*
import com.duocuc.serena.data.dataModel.EmotionalRegisterData

@Dao
interface RegistroEmocionalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmotion(register: EmotionalRegisterData)

    @Update
    suspend fun updateEmotion(register: EmotionalRegisterData)

    @Delete
    suspend fun deleteEmotion(register: EmotionalRegisterData)

    @Query("SELECT * FROM RegistroEmocionalUsuario")
    suspend fun getAllRegisters(): List<EmotionalRegisterData>

    @Query("SELECT * FROM RegistroEmocionalUsuario WHERE id = :id")
    suspend fun getRegisterById(id: Int): EmotionalRegisterData?
}
