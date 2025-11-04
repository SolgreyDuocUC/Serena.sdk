package com.duocuc.serena.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    var name = mutableStateOf("Usuario de Prueba")
    var email = mutableStateOf("usuario@email.com")
    var oldPassword = mutableStateOf("")
    var newPassword = mutableStateOf("")
    var imageUri = mutableStateOf<Uri?>(null)

    fun updateName(newName: String) {
        name.value = newName
    }

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updateOldPassword(value: String) {
        oldPassword.value = value
    }

    fun updateNewPassword(value: String) {
        newPassword.value = value
    }

    fun updateImage(newUri: Uri?) {
        imageUri.value = newUri
    }

    fun saveProfileChanges() {
        // Simulación de validación de contraseña anterior
        if (oldPassword.value.isNotEmpty() && oldPassword.value != "123456") {
            println("Contraseña actual incorrecta")
        } else {
            println("Perfil actualizado correctamente")
        }
    }
}
