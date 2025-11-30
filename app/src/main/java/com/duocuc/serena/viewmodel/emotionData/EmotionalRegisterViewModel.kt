package com.duocuc.serena.viewmodel.emotionData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duocuc.serena.data.dataModel.EmotionalRegisterData
import com.duocuc.serena.repository.EmotionalRegisterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

// Se asume que este constructor es manejado por una Factory o un sistema de inyección (como Hilt/Koin)
class EmotionalRegisterViewModel(
    private val repository: EmotionalRegisterRepository
) : ViewModel() {

    private val _registers = MutableStateFlow<List<EmotionalRegisterData>>(emptyList())
    val registers: StateFlow<List<EmotionalRegisterData>> get() = _registers

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    /**
     * Registra una nueva emoción con la descripción del día.
     */
    fun registerEmotion(idEmocion: Int, descripcion: String, fecha: LocalDate) {
        viewModelScope.launch {
            // Se asume que el repositorio ahora acepta la descripción
            val result = repository.registerEmotion(idEmocion, descripcion, fecha)
            result.onSuccess { loadRegisters() }
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

    /**
     * Actualiza una emoción existente, incluyendo la nueva descripción.
     */
    fun updateEmotion(id: Int, newIdEmocion: Int, newDescripcion: String, newFecha: LocalDate) {
        viewModelScope.launch {
            // Se asume que el repositorio ahora acepta la descripción
            val result = repository.updateEmotion(id, newIdEmocion, newDescripcion, newFecha)
            result.onSuccess { loadRegisters() }
                .onFailure { _error.value = it.message }
        }
    }

    /**
     * Elimina un registro por ID.
     */
    fun deleteEmotion(id: Int) {
        viewModelScope.launch {
            val result = repository.deleteEmotion(id)
            result.onSuccess { loadRegisters() }
                .onFailure { _error.value = it.message }
        }
    }

}