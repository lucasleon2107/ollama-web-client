package me.lucasleon.ollamawebclient.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "ollama")
class OllamaConfigProperties {
    lateinit var url: String
}
