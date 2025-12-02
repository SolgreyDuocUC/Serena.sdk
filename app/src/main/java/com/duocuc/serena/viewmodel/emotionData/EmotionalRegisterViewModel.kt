package com.duocuc.serena.viewmodel.emotionData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duocuc.serena.data.modelData.EmotionalRegister
import com.duocuc.serena.repository.EmotionalRegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class EmotionalRegisterViewModel(
    private val repository: EmotionalRegisterRepository
) : ViewModel() {

    private val _registers = MutableStateFlow<List<EmotionalRegister>>(emptyList())
    val registers: StateFlow<List<EmotionalRegister>> get() = _registers

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    /**
     * Registra una nueva emoción con la descripción del día.
     */
    fun registerEmotion(idEmocion: Int, descripcion: String, fecha: LocalDate) {
        viewModelScope.launch {
            val result = repository.registerEmotion(idEmocion, descripcion, fecha)
            result.onSuccess { loadRegisters() } // Recargar la lista después de un registro exitoso
                .onFailure { _error.value = it.message }
        }
    }

    /**
     * Carga todos los registros emocionales.
     */
    fun loadRegisters() {
        viewModelScope.launch {
            val result = repository.getAllRegisters()
            result.onSuccess { _registers.value = it }
                .onFailure { _error.value = it.message }
        }
    }

    // TODO: Implementar los métodos de actualizar y eliminar utilizando la API
}