package com.duocuc.serena.repository

import com.duocuc.serena.dao.UserDAO
import com.duocuc.serena.data.dataModel.UserData
import com.duocuc.serena.data.dataModel.UserActiveSession
import com.duocuc.serena.dao.UserSessionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDAO: UserDAO,
    private val userSessionDao: UserSessionDao
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    val activeUser: Flow<UserData?> = userSessionDao.getActiveSessionFlow().flatMapLatest { session ->
        if (session != null) {
            userDAO.findUserByIdFlow(session.activeUserId)
        } else {
            flowOf(null)
        }
    }

    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
    ): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val existingUser = userDAO.findByEmail(email)
                if (existingUser != null) {
                    Result.failure(Exception("Error de servidor simulado o email duplicado."))
                } else {
                    val newUser = UserData(
                        userEmail = email,
                        userPassword = password,
                        userName = name
                    )
                    userDAO.insertAll(newUser)
                    Result.success(true)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun loginUser(
        email: String,
        password: String
    ): Result<UserData?> {
        return withContext(Dispatchers.IO) {
            try {
                val user = userDAO.findByEmailAndPassword(email, password)
                if (user != null) {
                    userSessionDao.saveSession(UserActiveSession(activeUserId = user.id))
                }
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            userSessionDao.clearSession()
        }
    }

    suspend fun updateUser(user: UserData) {
        withContext(Dispatchers.IO) {
            userDAO.updateUser(user)
        }
    }
}