package com.jetbrains.greeting

import androidx.compose.ui.window.ComposeUIViewController
import com.jetbrains.greeting.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
    initKoin()
})
{
    App()
}