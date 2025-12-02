package com.duocuc.serena.data.modelData

data class EmotionalRegister(
    val id: Long,
    val date: String,
    val description: String?,
    val emotion: Emotion,
    val user: User
)