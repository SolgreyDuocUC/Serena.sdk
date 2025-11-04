package com.duocuc.serena.navigation

sealed class Route(val path: String) {
    object Splash : Route("splash")
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home")
    object Journal : Route("journal")
    object Analysis : Route("analysis")
    object Profile : Route("profile")
}
