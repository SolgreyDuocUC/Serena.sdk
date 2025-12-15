package com.duocuc.serena.data.remote.user.dto.user

import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: Long?,

    @SerializedName("userName")
    val userName: String,

    @SerializedName("userEmail")
    val userEmail: String,

    @SerializedName("userPassword")
    val userPassword: String?,

    @SerializedName("userAceptConditions")
    val userAceptConditions: Boolean?,

    @SerializedName("userImageUri")
    val userImageUri: String?
)