package com.duocuc.serena.remote.Dto

data class UserActiveSessionDto(
    val sessionId: Int,
    val activeUserId: Long
)