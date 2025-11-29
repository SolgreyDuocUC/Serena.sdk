package com.duocuc.serena.viewmodel.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel (app: Application) : AndroidViewModel (application = app) {

    fun toggleModo() = viewModelScope.launch {
        getApplication<Application>()
    }
}