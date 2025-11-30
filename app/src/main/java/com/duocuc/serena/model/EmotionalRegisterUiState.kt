package com.duocuc.serena.model

import java.time.LocalDate

data class EmotionalRegisterUiState(
    val id: Int? = null,
    val selectedDate: LocalDate? = null,
    val selectedEmotion: String = "",
    val selectedEmoji: String = "",
    val description: String = ""
)
