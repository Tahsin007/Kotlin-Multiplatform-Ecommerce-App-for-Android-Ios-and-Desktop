package com.jetbrains.greeting.presentation.Home

import com.jetbrains.greeting.data.ProductsItem
import io.ktor.client.HttpClient

sealed class HomeEvent{
//    data class showProducts(var engine: HttpClient):HomeEvent()
    object showProducts : HomeEvent()
}

data class HomeState(
    var products : List<ProductsItem>? = null
)

