package me.lucasleon.ollamawebclient.models.dtos.request


import kotlinx.serialization.Serializable

@Serializable
data class OllamaRequest(
    val model: String = "tinyllama",
    val prompt: String
)
