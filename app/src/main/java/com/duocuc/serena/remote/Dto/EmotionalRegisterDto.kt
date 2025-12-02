package com.duocuc.serena.remote.Dto

data class EmotionalRegisterDto(
    val id: Long,
    val date: String,
    val description: String?,
    val emotion: EmotionDto,
    val user: UserDto
)