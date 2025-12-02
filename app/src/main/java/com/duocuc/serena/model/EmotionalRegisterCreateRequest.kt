package com.duocuc.serena.model

data class EmotionalRegisterCreateRequest(
    val date: String,          // ISO: "2025-01-20"
    val description: String? = null,
    val emotionId: Long,
    val userId: Long
)