package com.duocuc.serena.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.duocuc.serena.navigation.Route

// ----------------------
// MODELOS Y CONFIGURACIÓN
// ----------------------
data class BottomNavItem(
    val route: Route,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val label: String
)

private val bottomNavItems = listOf(
    BottomNavItem(Route.Home, Icons.Filled.Home, "Inicio"),
    BottomNavItem(Route.Journal, Icons.Filled.SelfImprovement, "Diario"),
    BottomNavItem(Route.Analysis, Icons.Filled.Insights, "Análisis")
)

@Composable
fun BottomNavBar(navController: NavController) {
    var selectedItem by remember { mutableStateOf(Route.Home.path) }

    NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem == item.route.path,
                onClick = {
                    selectedItem = item.route.path
                    navController.navigate(item.route.path) {
                        popUpTo(Route.Home.path) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun CalendarView() {
    val daysOfWeek = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Noviembre 2025",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Icon(Icons.Default.CalendarMonth, contentDescription = "Cambiar mes")
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(36.dp)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        val dummyDays = listOf(
            null, null, null, null, 1, 2, 3,
            4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17,
            18, 19, 20, 21, 22, 23, 24,
            25, 26, 27, 28, 29, 30, null
        )
        val rows = dummyDays.chunked(7)
        val today = 18

        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                row.forEach { day ->
                    val isToday = day == today
                    val hasEntry = day != null && day % 5 == 0

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(18.dp))
                            .background(
                                when {
                                    isToday -> MaterialTheme.colorScheme.primary
                                    hasEntry -> MaterialTheme.colorScheme.tertiaryContainer
                                    else -> MaterialTheme.colorScheme.surface
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (day != null) {
                            Text(
                                text = day.toString(),
                                color = if (isToday)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isToday) FontWeight.ExtraBold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppScreen(nav: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Serena: Tu Espacio Mental",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = { BottomNavBar(navController = nav) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido de vuelta. Tu estado mental es clave.",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 25.dp)
            )

            CalendarView()

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { nav.navigate(Route.Journal.path) },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Registrar Emoción de Hoy")
            }
        }
    }
}
