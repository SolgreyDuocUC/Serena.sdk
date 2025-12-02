package com.duocuc.serena.remote.Dto

data class EmotionalRegisterCreateRequestNested(
    val date: String,
    val description: String?,
    val emotion: IdWrapper,
    val user: IdWrapper
)

data class IdWrapper(val id: Long)