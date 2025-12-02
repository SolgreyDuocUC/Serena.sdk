package com.duocuc.serena.remote.Dto

data class EmotionDto(
    val id: Long,
    val name: String,
    val description: String,
    val color: String,
    val textColor: String,
    val icon: String
)