package com.duocuc.serena.data.remote.user.dto.user

import com.duocuc.serena.data.dataModel.UserData

fun UserDto.toLocal(): UserData {
    return UserData(
        id = id ?: 0,
        userName = userName,
        userEmail = userEmail,
        userPassword = userPassword ?: "",
        userAceptConditions = userAceptConditions ?: false,
        userImageUri = userImageUri
    )
}