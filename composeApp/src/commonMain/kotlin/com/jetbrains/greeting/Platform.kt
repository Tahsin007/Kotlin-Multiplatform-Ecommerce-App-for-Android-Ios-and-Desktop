package com.jetbrains.greeting

interface Platform {
    val UIDevice: Any
    val name: String
}

expect fun isDesktop():Boolean

//expect fun getPlatform(): Platform