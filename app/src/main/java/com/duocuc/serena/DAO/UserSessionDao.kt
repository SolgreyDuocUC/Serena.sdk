package com.duocuc.serena.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.duocuc.serena.data.UserActiveSession
import kotlinx.coroutines.flow.Flow

@Dao
interface UserSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(session: UserActiveSession)

    @Query("SELECT * FROM SesionActiva LIMIT 1")
    fun getActiveSessionFlow(): Flow<UserActiveSession?>

    @Query("DELETE FROM SesionActiva")
    suspend fun clearSession(): Int

}