package com.duocuc.serena.DAO


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.duocuc.serena.data.EmotionalRegisterData

@Dao
interface RegistroEmocionalDao {

    @Insert
    suspend fun insertEmotion(register: EmotionalRegisterData)

    @Query("SELECT * FROM RegistroEmocionalUsuario")
    suspend fun getAllRegisters(): List<EmotionalRegisterData>

    @Query("SELECT * FROM RegistroEmocionalUsuario WHERE idEmocion = :emotionId")
    suspend fun getRegistersByEmotion(emotionId: Int): List<EmotionalRegisterData>
}
