package com.nuncamaria.learningandroidarchitecture.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {

    /**
     * Creates a new [HttpClient] instance with the given [engine]. The engine is used to make the HTTP requests.
     */
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            /**
             * This block make easier the debugging.
             */
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            /**
             * This block is used to configure the serialization plugin.
             * ignoreUnknownKeys is set to true to ignore unknown keys in the JSON response, to prevent our app crash.
             * This is useful when the API changes and we don't want to update our responses.
             * Or when we don't need all the fields in the data class response.
             */
            install(ContentNegotiation) {
                json(
                    json = Json { ignoreUnknownKeys = true }
                )
            }
            /**
             * This block is used to configure the default request, in our case, every single request will communicate by json.
             */
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}