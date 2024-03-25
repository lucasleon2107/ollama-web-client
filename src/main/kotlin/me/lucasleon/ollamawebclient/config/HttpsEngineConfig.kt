package me.lucasleon.ollamawebclient.config

import io.ktor.client.engine.cio.CIO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HttpsEngineConfig {
    @Bean
    fun engine() = CIO.create()
}
