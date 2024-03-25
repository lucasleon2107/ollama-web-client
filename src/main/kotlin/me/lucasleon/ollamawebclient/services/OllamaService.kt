package me.lucasleon.ollamawebclient.services

import me.lucasleon.ollamawebclient.models.dtos.request.OllamaRequest
import me.lucasleon.ollamawebclient.requesters.OllamaRequester
import org.springframework.stereotype.Service

@Service
class OllamaService(
    private val ollamaRequester: OllamaRequester
) {
    fun getOllamaResponse(ollamaRequest: OllamaRequest) = ollamaRequester.getOllamaResponse(ollamaRequest)
}
