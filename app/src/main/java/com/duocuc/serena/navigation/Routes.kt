package com.duocuc.serena.navigation

sealed class Route(val path: String){
    data object Root: Route(path = "root")
    data object Home: Route(path = "home")
    data object Perfil : Route("perfil")
}