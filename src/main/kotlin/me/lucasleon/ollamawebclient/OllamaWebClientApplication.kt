package me.lucasleon.ollamawebclient

import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.page.Push
import com.vaadin.flow.server.ErrorHandler
import com.vaadin.flow.server.ServiceInitEvent
import com.vaadin.flow.server.VaadinServiceInitListener
import com.vaadin.flow.theme.Theme
import com.vaadin.flow.theme.lumo.Lumo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@Push
@Theme(variant = Lumo.DARK)
@SpringBootApplication
class OllamaWebClientApplication : AppShellConfigurator

fun main(args: Array<String>) {
    runApplication<OllamaWebClientApplication>(*args)
}

class VaadinInitListener : VaadinServiceInitListener {
    override fun serviceInit(event: ServiceInitEvent) {
        event.source.addSessionInitListener {
            it.session.errorHandler = ErrorHandler { event ->
                Notification("Internal error: ${event.throwable}", 5000, Notification.Position.TOP_CENTER).apply {
                    addThemeVariants(NotificationVariant.LUMO_ERROR)
                }.open()
            }
        }
    }
}
