package me.lucasleon.ollamawebclient.models.dtos.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OllamaResponse(
    @SerialName("created_at")
    val createdAt: String? = null,
    val done: Boolean? = null,
    val model: String? = null,
    val response: String? = null
)
