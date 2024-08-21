package com.jetbrains.greeting.presentation.Home

import com.jetbrains.greeting.data.ProductsItem
import io.ktor.client.HttpClient

sealed class HomeEvent{
    object showProducts : HomeEvent()
    data class showSingleProduct (val productId:String):HomeEvent()
}

data class HomeState(
    var products : List<ProductsItem>? = null,
    var singleProduct: ProductsItem? = null
)

