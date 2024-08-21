package com.jetbrains.greeting.presentation.Navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jetbrains.greeting.model.repository.HomeRepository
import com.jetbrains.greeting.presentation.Home.HomeScreen
import com.jetbrains.greeting.presentation.Home.HomeViewModel
import com.jetbrains.greeting.presentation.Product.ProductDetailsScreen
import com.jetbrains.greeting.presentation.Profile.ProfileScreen
import com.jetbrains.greeting.presentation.Search.SearchScreen
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
            modifier = Modifier.padding(innerPadding),
            enterTransition ={
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            }

        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(BottomNavItem.Search.route) {
                SearchScreen()
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = "${BottomNavItem.ProductDetails.route}/{productId}",
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                if (productId != null) {
                    ProductDetailsScreen(productId,navController)
                }
            }
        }
    }
}



sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
    object ProductDetails : BottomNavItem("productDetails", Icons.Default.Person, "Profile")
}

@Composable
fun BottomNavigation(
    items: List<BottomNavItem>,
    navController: NavHostController,
    currentRoute:String
) {

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = navController.currentBackStackEntry?.destination?.route == item.route,
                onClick = {
                    navigateBottomBar(navController,item.route)
//                    navController.navigate(item.route)
                }
            )
        }
    }
}

private fun navigateBottomBar(navController: NavController, destination: String) {
    navController.navigate(destination) {
        navController.graph.startDestinationRoute?.let { route ->
            popUpTo(BottomNavItem.Home.route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private val NavController.shouldShowBottomBar
    get() = when (this.currentBackStackEntry?.destination?.route) {
        BottomNavItem.Home.route,
        BottomNavItem.Search.route,
        BottomNavItem.Profile.route,
        -> true

        else -> false
    }

