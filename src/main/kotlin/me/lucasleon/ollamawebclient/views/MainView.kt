package me.lucasleon.ollamawebclient.views

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.appLayout
import com.github.mvysny.karibudsl.v10.content
import com.github.mvysny.karibudsl.v10.messageInput
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.DetachEvent
import com.vaadin.flow.component.messages.MessageInput
import com.vaadin.flow.component.messages.MessageInputI18n
import com.vaadin.flow.component.messages.MessageList
import com.vaadin.flow.component.messages.MessageListItem
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.router.Route
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import me.lucasleon.ollamawebclient.models.dtos.request.OllamaRequest
import me.lucasleon.ollamawebclient.services.OllamaService
import me.lucasleon.ollamawebclient.vaadin

@Route
class MainView(
    private val ollamaService: OllamaService
) : KComposite(), CoroutineScope {
    private val uiCoroutineScope = SupervisorJob()
    private val uiCoroutineContext = vaadin()

    init {
        val messageListComponent = MessageList().apply {
            maxWidth = "48rem"
            setWidthFull()
        }

        ui {
            appLayout {
                content {
                    verticalLayout {
                        setHeightFull()
                        isSpacing = false
                        justifyContentMode = FlexComponent.JustifyContentMode.BETWEEN
                        verticalLayout {
                            setWidthFull()
                            isPadding = false
                            isSpacing = false
                            alignItems = FlexComponent.Alignment.CENTER
                            style["overflow-y"] = "auto"
                            add(messageListComponent)
                        }
                        verticalLayout {
                            setSizeUndefined()
                            setWidthFull()
                            isPadding = false
                            isSpacing = false
                            alignItems = FlexComponent.Alignment.CENTER
                            messageInput {
                                i18n = MessageInputI18n().apply {
                                    message = "Message Ollama.."
                                    send = "Send"
                                }
                                maxWidth = "48rem"
                                width = "100%"
                                addSubmitListener {
                                    isEnabled = false
                                    val userMessageListItem = MessageListItem().apply {
                                        text = it.value
                                        userName = System.getProperty("user.name").uppercase()
                                    }

                                    val messageListItems = messageListComponent.items.toMutableList()
                                    messageListItems += userMessageListItem

                                    messageListComponent.setItems(messageListItems)

                                    addOllamaMessage(
                                        prompt = it.value,
                                        messages = messageListItems,
                                        messageList = messageListComponent,
                                        messageInput = this
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun addOllamaMessage(
        prompt: String,
        messages: MutableList<MessageListItem>,
        messageList: MessageList,
        messageInput: MessageInput,
    ) = launch {
        val ollamaMessageListItem = MessageListItem().apply {
            text = ""
            userName = "Ollama"
            userImage = "https://djeqr6to3dedg.cloudfront.net/repo-logos/ollama/ollama/live/logo-1701412810306.png"
        }

        messages += ollamaMessageListItem

        ollamaService
            .getOllamaResponse(
                OllamaRequest(
                    prompt = prompt
                )
            )
            .onCompletion {
                messageInput.isEnabled = true
            }
            .collect { ollamaResponse ->
                getUI().ifPresent { ui ->
                    ui.access {
                        ollamaMessageListItem.text += ollamaResponse.response
                        messageList.setItems(messages)
                    }
                }
            }
    }

    override val coroutineContext: CoroutineContext
        get() = uiCoroutineContext + uiCoroutineScope

    override fun onDetach(detachEvent: DetachEvent) {
        uiCoroutineScope.cancel()
        super.onDetach(detachEvent)
    }
}
