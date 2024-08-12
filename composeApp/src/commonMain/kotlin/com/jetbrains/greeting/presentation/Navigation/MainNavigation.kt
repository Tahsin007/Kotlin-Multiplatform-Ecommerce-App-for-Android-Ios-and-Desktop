package com.jetbrains.greeting.presentation.Navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetbrains.greeting.presentation.Home.HomeScreen
import com.jetbrains.greeting.presentation.Home.HomeViewModel
import io.ktor.client.HttpClient


@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val startDestination = BottomNavItem.Home.route

    Scaffold(
        bottomBar = {
            BottomNavigation(
                items = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Profile),
                navController = navController,
                currentRoute = navController.currentBackStackEntry?.destination?.route ?: startDestination
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(navController = navController, viewModel = HomeViewModel())
            }
        }
    }
}



sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}

@Composable
fun BottomNavigation(
    items: List<BottomNavItem>,
    navController: NavHostController,
    currentRoute: String
) {

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}
