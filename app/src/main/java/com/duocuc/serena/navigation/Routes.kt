package com.duocuc.serena.navigation

sealed class Route(val path: String) {
    object Splash : Route("splash")
    object Login : Route("login")
    object Register : Route("register")
    object Dashboard : Route("dashboard")
    object Profile : Route("profile")
}