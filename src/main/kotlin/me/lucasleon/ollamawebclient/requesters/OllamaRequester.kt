package me.lucasleon.ollamawebclient.requesters

import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import me.lucasleon.ollamawebclient.commons.RestClient.Companion.restClient
import me.lucasleon.ollamawebclient.models.dtos.request.OllamaRequest
import me.lucasleon.ollamawebclient.models.dtos.response.OllamaResponse
import org.springframework.stereotype.Component

@Component
class OllamaRequester(
    engine: HttpClientEngine
) {
    private val url = "http://localhost:11434/api/generate"
    private val restClient = restClient(engine)

    fun getOllamaResponse(ollamaRequest: OllamaRequest) = flow {
        restClient.preparePost(url) {
            setBody(ollamaRequest)
        }.execute { httpResponse ->
            val channel: ByteReadChannel = httpResponse.body()
            val jsonParser = Json { ignoreUnknownKeys = true }
            while (!channel.isClosedForRead) {
                val line = channel.readUTF8Line()
                if (line != null) {
                    emit(jsonParser.decodeFromString<OllamaResponse>(line))
                }
            }
        }
    }
}
