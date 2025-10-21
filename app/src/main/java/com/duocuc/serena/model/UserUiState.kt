package com.duocuc.serena.model

import android.R

data class UserUiState (

    val userName: String = "",
    val userLastName: String = "",
    val userAge: R.integer,
    val userEmail: String = "",
    val userPassword: String = "",
    val userAceptConditions: Boolean = true

)