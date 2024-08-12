package com.jetbrains.greeting


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.jetbrains.greeting.presentation.Navigation.MainNavigation

@Composable
fun App() {
    MaterialTheme {
        MainNavigation()
    }
}