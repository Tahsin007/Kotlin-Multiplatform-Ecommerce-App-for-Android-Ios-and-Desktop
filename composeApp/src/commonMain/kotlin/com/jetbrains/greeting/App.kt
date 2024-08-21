package com.jetbrains.greeting


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.jetbrains.greeting.presentation.Home.HomeViewModel
import com.jetbrains.greeting.presentation.Navigation.MainNavigation
import org.koin.compose.KoinContext

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            MainNavigation()
        }
    }
}