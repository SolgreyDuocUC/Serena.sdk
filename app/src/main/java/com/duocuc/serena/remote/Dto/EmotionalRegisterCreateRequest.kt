package com.duocuc.serena.remote.Dto

data class EmotionalRegisterCreateRequest(
    val date: String,
    val description: String?,
    val emotionId: Long,
    val userId: Long
)