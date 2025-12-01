@file:Suppress("DEPRECATION")

package com.duocuc.serena.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.duocuc.serena.factory.ViewModelFactory
import com.duocuc.serena.navigation.Route
import com.duocuc.serena.viewmodel.profile.SessionViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

// ------------------------- BOTTOM NAV ------------------------

data class BottomNavItem(
    val route: Route,
    val icon: ImageVector,
    val label: String
)

private val bottomNavItems = listOf(
    BottomNavItem(Route.EmotionalRegistered, Icons.Filled.FavoriteBorder, "Diario"),
    BottomNavItem(Route.Home, Icons.Filled.CalendarToday, "Calendario"),
    BottomNavItem(Route.Profile, Icons.Filled.AccountCircle, "Usuario"),
    BottomNavItem(Route.LearningPath, Icons.Filled.School, "Aprendizaje")
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    var selectedItem by remember { mutableStateOf(currentRoute ?: Route.Home.path) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = currentRoute?.startsWith(item.route.path.split("/").first()) == true

            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = isSelected,
                onClick = {
                    val routePath = if (item.route == Route.EmotionalRegistered) {
                        Route.EmotionalRegistered.createRoute(LocalDate.now())
                    } else {
                        item.route.path
                    }

                    if (currentRoute != routePath) {
                        selectedItem = item.route.path
                        navController.navigate(routePath) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
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

// ------------------------- DRAWER ------------------------

data class DrawerItem(val label: String, val icon: ImageVector)

private val drawerItems = listOf(
    DrawerItem("Ver registro emocional", Icons.Filled.Book),
    DrawerItem("Leer mensaje del día", Icons.Filled.WbSunny),
    DrawerItem("Ruta de aprendizaje emocional", Icons.Filled.School),
    DrawerItem("Configuración de usuario", Icons.Filled.Settings)
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppScreen(
    nav: NavController,
    sessionViewModel: SessionViewModel = viewModel(factory = ViewModelFactory())
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val activeUser by sessionViewModel.activeUser.collectAsState()
    val userName = activeUser?.userName ?: "Usuario"
    val encouragingMessage = "¡Ancla tu mente al presente, $userName! Aquí y ahora estás bien"

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                drawerTonalElevation = 4.dp
            ) {
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Menú principal",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(16.dp)
                )

                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = false,
                        onClick = {
                            when (item.label) {
                                "Configuración de usuario" -> nav.navigate(Route.Profile.path)
                                "Ver registro emocional" -> nav.navigate(Route.EmotionalRegistered.createRoute(LocalDate.now()))
                                "Leer mensaje del día" -> nav.navigate(Route.MessageOfDay.path)
                                "Ruta de aprendizaje emocional" -> nav.navigate(Route.LearningPath.path)
                            }
                            coroutineScope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                Spacer(Modifier.weight(1f))

                HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

                NavigationDrawerItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesión") },
                    label = { Text("Cerrar sesión") },
                    selected = false,
                    onClick = {
                        sessionViewModel.logout()
                        nav.navigate(Route.Login.path) {
                            popUpTo(Route.Home.path) { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menú")
                        }
                    },
                    title = {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = "Hola, $userName 👋",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
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
                Spacer(Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = encouragingMessage,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Calendario Emocional",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                Text(
                    text = "Visualiza y registra tus emociones cada día",
                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    CalendarContent(nav)
                }
            }
        }
    }
}

// ------------------------- CALENDARIO ------------------------

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarContent(navController: NavController) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    val today = LocalDate.now()
    val daysOfWeek = listOf("D", "L", "M", "X", "J", "V", "S")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { currentMonth = currentMonth.minusMonths(1) },
                modifier = Modifier.size(40.dp)
            ) { Icon(Icons.Filled.ChevronLeft, contentDescription = "Mes anterior") }

            Text(
                "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale("es")).replaceFirstChar { it.uppercase() }} ${currentMonth.year}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )

            // Deshabilitar botón de mes siguiente si se está en el mes actual o posterior
            val isCurrentMonthOrFuture = currentMonth >= YearMonth.now()
            IconButton(
                onClick = { currentMonth = currentMonth.plusMonths(1) },
                enabled = !isCurrentMonthOrFuture,
                modifier = Modifier.size(40.dp)
            ) { Icon(Icons.Filled.ChevronRight, contentDescription = "Mes siguiente") }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        CalendarGrid(currentMonth, today) { selectedDate ->
            navController.navigate(Route.EmotionalRegistered.createRoute(selectedDate))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    currentMonth: YearMonth,
    today: LocalDate,
    onDayClick: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = currentMonth.lengthOfMonth()

    val daysList = (0 until 42).map { index ->
        val dayNumber = index - startDayOfWeek + 1
        if (dayNumber in 1..daysInMonth) currentMonth.atDay(dayNumber) else null
    }

    val rows = daysList.chunked(7)

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val totalWidth = this.maxWidth
        val dayCellSize = (totalWidth / 7)

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            rows.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { date ->
                        DayCellAlternative(date, today, dayCellSize, onDayClick)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCellAlternative(
    date: LocalDate?,
    today: LocalDate,
    size: Dp,
    onClick: (LocalDate) -> Unit
) {
    val day = date?.dayOfMonth
    val isSelectable = date != null && (date.isBefore(today) || date.isEqual(today))
    val isToday = date?.isEqual(today)

    val backgroundColor = when {
        date == null -> Color.Transparent
        isToday == true -> MaterialTheme.colorScheme.primary
        !isSelectable -> Color.LightGray.copy(alpha = 0.3f)
        else -> MaterialTheme.colorScheme.surface
    }

    val contentColor = when {
        date == null -> Color.Transparent
        isToday == true -> MaterialTheme.colorScheme.onPrimary
        !isSelectable -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f) // Color para texto de días futuros
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(enabled = isSelectable) {
                date?.let { onClick(it) }
            },
        contentAlignment = Alignment.Center
    ) {
        if (day != null) {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                color = contentColor
            )
        }
    }
}