package com.duocuc.serena.navigation

sealed class Route(val path: String) {
    object Load : Route("load")
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home")
    object Calendar : Route("calendar")
    object Journal : Route("journal")
    object MessageOfDay : Route("message_day")
    object LearningPath : Route("learning_path")
    object Settings : Route("settings")
    object Profile : Route("profile")
    object Help : Route("help")
    object About : Route("about")
    object Logout : Route("logout")

    object EmotionalRegistered : Route(path="emotional_registered")
}



