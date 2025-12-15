package com.duocuc.serena.data.remote.user.dto.auth

import com.google.gson.annotations.SerializedName

data class AuthResponseDto(
    @SerializedName("accessToken")
    val accessToken: String,

    @SerializedName("refreshToken")
    val refreshToken: String
)