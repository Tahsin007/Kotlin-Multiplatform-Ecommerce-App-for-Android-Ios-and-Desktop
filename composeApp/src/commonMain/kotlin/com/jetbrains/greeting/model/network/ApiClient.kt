package com.jetbrains.greeting.model.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


val httpClient = HttpClient {
    install(HttpTimeout) {
        val timeout = 30000L
        connectTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        socketTimeoutMillis = timeout
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }
//    install(Logging){
//        level = LogLevel.ALL
//    }
}
//private val client = HttpClient {
//    expectSuccess = true
//    install(HttpTimeout) {
//        val timeout = 30000L
//        connectTimeoutMillis = timeout
//        requestTimeoutMillis = timeout
//        socketTimeoutMillis = timeout
//    }
//    install(DefaultRequest) {
//        header(HttpHeaders.Accept, ContentType.Application.Json)
//        header(HttpHeaders.ContentType, ContentType.Application.Json)
//    }
//    install(ContentNegotiation) {
//        json(Json { isLenient = true; ignoreUnknownKeys = true })
//    }
//}

//fun createHttpClient(engine:HttpClientEngine):HttpClient{
//    return HttpClient(engine){
//        install(Logging){
//            level = LogLevel.ALL
//        }
//        install(ContentNegotiation){
//            json(Json {
//                prettyPrint = true
//                ignoreUnknownKeys = true
//            })
//        }
//
//    }
//}