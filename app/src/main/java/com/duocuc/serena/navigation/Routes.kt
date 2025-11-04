package com.duocuc.serena.navigation

sealed class Route(val path: String) {
    object Load : Route("load")                     // Pantalla inicial (Splash o Loading)
    object Login : Route("login")                   // Inicio de sesión
    object Register : Route("register")             // Registro de usuario
    object Home : Route("home")                     // Pantalla principal
    object Calendar : Route("calendar")             // Calendario emocional
    object Journal : Route("journal")               // Registro emocional
    object MessageOfDay : Route("message_day")      // Mensaje del día
    object LearningPath : Route("learning_path")    // Ruta de aprendizaje emocional
    object Devotional : Route("devotional")         // Devocional diario
    object Settings : Route("settings")             // Configuración general
    object Profile : Route("profile")               // Configuración de usuario
    object Help : Route("help")                     // Ayuda o soporte
    object About : Route("about")                   // Acerca de la app
    object Logout : Route("logout")                 // Cerrar sesión
}



