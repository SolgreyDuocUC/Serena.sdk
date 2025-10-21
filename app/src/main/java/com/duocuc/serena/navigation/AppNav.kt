package com.duocuc.serena.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun AppNavHost (nav: NavHostController){

    val configVM: ConfigViewModel = viewModel()
    val perfilVM: PerfilViewModel = viewModel()
    val productoVM: ProductoViewModel = viewModel()

    NavHost(navController = nav, startDestination = Route.Home.path, route = Route.Root.path) {
        composable(Route.Home.path) {
            HomeScreen(nav, configVM)
        }
        composable(Route.Perfil.path) {
            PerfilScreen(nav, perfilVM)
        }
        composable(Route.Registrar.path) {
            RegistroScreen(nav, productoVM)
        }
        composable(Route.Resumen.path) {
            ResumenScreen(nav, productoVM)
        }
    }

}