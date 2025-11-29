package com.duocuc.serena.viewmodel.emotionData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duocuc.serena.data.dataModel.EmotionalRegisterData
import com.duocuc.serena.repository.EmotionalRegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class EmotionalRegisterViewModel(
    private val repository: EmotionalRegisterRepository
) : ViewModel() {

    private val _registers = MutableStateFlow<List<EmotionalRegisterData>>(emptyList())
    val registers: StateFlow<List<EmotionalRegisterData>> get() = _registers

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun registerEmotion(idEmocion: Int, fecha: LocalDate) {
        viewModelScope.launch {
            val result = repository.registerEmotion(idEmocion, fecha)
            result.onSuccess { loadRegisters() }
                .onFailure { _error.value = it.message }
        }
    }

    fun loadRegisters() {
        viewModelScope.launch {
            val result = repository.getAllRegisters()
            result.onSuccess { _registers.value = it }
                .onFailure { _error.value = it.message }
        }
    }

    fun updateEmotion(id: Int, newIdEmocion: Int, newFecha: LocalDate) {
        viewModelScope.launch {
            val result = repository.updateEmotion(id, newIdEmocion, newFecha)
            result.onSuccess { loadRegisters() }
                .onFailure { _error.value = it.message }
        }
    }

    fun deleteEmotion(id: Int) {
        viewModelScope.launch {
            val result = repository.deleteEmotion(id)
            result.onSuccess { loadRegisters() }
                .onFailure { _error.value = it.message }
        }
    }

}