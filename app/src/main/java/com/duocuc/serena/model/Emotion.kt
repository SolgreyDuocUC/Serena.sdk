package com.duocuc.serena.model

import androidx.compose.ui.graphics.Color

data class Emotion(
    val id: Int,
    val name: String,
    val description: String,
    val color: Color,
    val textColor: Color
)
