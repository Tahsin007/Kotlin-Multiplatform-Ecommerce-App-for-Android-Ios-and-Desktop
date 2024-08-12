package com.jetbrains.greeting.model.repository

import com.jetbrains.greeting.data.ProductsItem
import com.jetbrains.greeting.model.network.httpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class HomeRepository {

    suspend fun getProductsApi():List<ProductsItem>{
        val response = httpClient.get("https://fakestoreapi.com/products")
        println(response)
//        if(response.status.value==200){
//            return response.body()
//        }
        return response.body()
    }
}