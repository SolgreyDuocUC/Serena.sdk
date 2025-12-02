package com.duocuc.serena.data.modelData

data class EmotionCreateRequest(
    val name: String,
    val description: String,
    val color: String,
    val textColor: String,
    val icon: String
)