package me.lucasleon.ollamawebclient.commons

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Component
class RestClient {
    companion object {
        fun restClient(
            engine: HttpClientEngine,
            config: HttpClientConfig<*>.() -> Unit = {}
        ) = HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 120000
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
            config()
        }
    }
}
