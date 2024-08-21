package com.jetbrains.greeting.model.repository

import com.jetbrains.greeting.core.BaseUrl
import com.jetbrains.greeting.core.NetworkErrorHandler
import com.jetbrains.greeting.core.ResultWrapper
import com.jetbrains.greeting.data.ProductsItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.call.body
import io.ktor.http.isSuccess

class HomeRepository(private val httpClient: HttpClient) :BaseRepository{

    override suspend fun getProductsApi():List<ProductsItem>{
        return try {
            val response: HttpResponse = httpClient.get("${BaseUrl}products")
            return response.body()
        }catch (e :Exception){
            NetworkErrorHandler.handleException(e)
        }
    }

    override suspend fun getSingleProduct(productId: String): ProductsItem {
        return try{
            val response: HttpResponse = httpClient.get("${BaseUrl}products/$productId")
            println("Response : ${response.body<HttpResponse>()}" )
            return response.body()
        }catch (e:Exception){
            NetworkErrorHandler.handleException(e)
        }

    }
}
