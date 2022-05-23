package ru.mobileup.integrationhms

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.koin
import ru.mobileup.features.root.ui.RootUi
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.features.notification.data.DeeplinkParser
import ru.mobileup.features.root.createRootComponent
import ru.mobileup.features.root.ui.RootComponent

class MainActivity : ComponentActivity() {

    private lateinit var rootComponent: RootComponent
    private lateinit var deeplinkParser: DeeplinkParser

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val deeplink = deeplinkParser.getDeeplink(intent)
        deeplink?.let { rootComponent.onDeepLink(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val componentFactory = application.koin.get<ComponentFactory>()
        rootComponent = componentFactory.createRootComponent(defaultComponentContext())

        deeplinkParser = application.koin.get()
        val deeplink = deeplinkParser.getDeeplink(intent)
        deeplink?.let { rootComponent.onDeepLink(it) }

        setContent {
            AppTheme {
                RootUi(rootComponent)
            }
        }
    }
}