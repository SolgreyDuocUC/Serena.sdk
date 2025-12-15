package com.duocuc.serena.repository

import com.duocuc.serena.data.dataModel.UserData
import com.duocuc.serena.data.remote.user.Auth.AuthDataStore
import com.duocuc.serena.data.remote.user.UserService
import com.duocuc.serena.data.remote.user.dto.user.toLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class UserRepository(
    private val userService: UserService,
    private val authDataStore: AuthDataStore
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    val activeUser: Flow<UserData?> =
        authDataStore.userId.flatMapLatest { userId ->

            if (userId == null) {
                flowOf(null)
            } else {
                flowOf(
                    userService.findById(userId)?.toLocal()
                )
            }
        }

    suspend fun updateUser(user: UserData) {
        withContext(Dispatchers.IO) {
            // TODO: userService.updateUser(user.toRemote())
        }
    }
}
