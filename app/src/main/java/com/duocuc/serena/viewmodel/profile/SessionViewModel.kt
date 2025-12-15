package com.duocuc.serena.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duocuc.serena.data.dataModel.UserData
import com.duocuc.serena.data.remote.user.Auth.AuthRepository
import com.duocuc.serena.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SessionViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val activeUser: StateFlow<UserData?> =
        userRepository.activeUser.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
