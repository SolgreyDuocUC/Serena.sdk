package com.duocuc.serena.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.duocuc.serena.Graph
import com.duocuc.serena.viewmodel.auth.LoginViewModel
import com.duocuc.serena.viewmodel.auth.RegisterViewModel
import com.duocuc.serena.viewmodel.diary.EmotionalRegisterViewModel
import com.duocuc.serena.viewmodel.profile.ProfileViewModel
import com.duocuc.serena.viewmodel.profile.SessionViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val sessionViewModel = SessionViewModel(Graph.userRepository)

        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Graph.userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Graph.userRepository) as T
            }
            modelClass.isAssignableFrom(SessionViewModel::class.java) -> {
                sessionViewModel as T
            }
            modelClass.isAssignableFrom(EmotionalRegisterViewModel::class.java) -> {
                EmotionalRegisterViewModel(Graph.emotionalRegisterRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(Graph.userRepository, sessionViewModel) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}