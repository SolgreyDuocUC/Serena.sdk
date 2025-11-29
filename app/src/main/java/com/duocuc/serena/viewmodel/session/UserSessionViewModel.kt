package com.duocuc.serena.viewmodel.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duocuc.serena.data.dataModel.UserData
import com.duocuc.serena.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserSessionViewModel(private val userRepository: UserRepository) : ViewModel() {
    val activeUser: StateFlow<UserData?> = userRepository.activeUser.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}