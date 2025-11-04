package com.duocuc.serena.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.duocuc.serena.navigation.Route

data class BottomNavItem(
    val route: Route,
    val icon: ImageVector,
    val label: String
)

private val bottomNavItems = listOf(
    BottomNavItem(Route.Home, Icons.Filled.FavoriteBorder, "Diario"),
    BottomNavItem(Route.Calendar, Icons.Filled.CalendarToday, "Calendario"),
    BottomNavItem(Route.Settings, Icons.Filled.Settings, "Config")
)

@Composable
fun BottomNavBar(navController: NavController) {
    var selectedItem by remember { mutableStateOf(Route.Calendar.path) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier.height(80.dp)
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedItem == item.route.path,
                onClick = {
                    selectedItem = item.route.path
                    navController.navigate(item.route.path) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Composable
fun HomeAppScreen(nav: NavController) {
    Scaffold(
        bottomBar = { BottomNavBar(navController = nav) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hola, Usuario",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "Cada día es una nueva oportunidad para crecer 🏋️",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray.copy(alpha = 0.7f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Calendario Emocional",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Visualiza y registra tus emociones cada día",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                CalendarContent()
            }
        }
    }
}

@Composable
fun CalendarContent() {
    val daysOfWeek = listOf("Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        val dummyDays = listOf(
            null, null, null, null, null, null, 1,
            2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15,
            16, 17, 18, 19, 20, 21, 22,
            23, 24, 25, 26, 27, 28, 29,
            30, null, null, null, null, null, null
        )
        val rows = dummyDays.chunked(7)
        val today = 4

        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val totalWidth = this.maxWidth
            val dayCellSize = totalWidth / 7

            rows.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    row.forEach { day ->
                        DayCellAlternative(day, day == today, dayCellSize)
                    }
                }
            }
        }
    }
}

@Composable
fun DayCellAlternative(day: Int?, isSelected: Boolean, size: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .width(size)
            .height(size)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        if (day != null) {
            val hasEntry = day > 4 && day % 3 == 0
            Box(
                modifier = Modifier
                    .fillMaxSize(0.9f)
                    .clip(CircleShape)
                    .background(
                        when {
                            isSelected -> MaterialTheme.colorScheme.primary
                            hasEntry -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                            else -> Color.Transparent
                        }
                    )
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.toString(),
                    color = if (isSelected)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if (day < 4) 0.3f else 1f),
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal
                )
            }
        }
    }
}