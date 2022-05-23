package ru.mobileup.features.root.ui

import android.os.Parcelable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import kotlinx.parcelize.Parcelize
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.message.createMessagesComponent
import ru.mobileup.core.utils.toComposeState
import ru.mobileup.features.notification.domain.DeepLink
import ru.mobileup.features.photo.createPhotoListComponent

class RealRootComponent(
    componentContext: ComponentContext,
    private val componentFactory: ComponentFactory
) : ComponentContext by componentContext, RootComponent {

    private val router = router<ChildConfig, RootComponent.Child>(
        initialConfiguration = ChildConfig.PhotoList,
        handleBackButton = true,
        childFactory = ::createChild
    )

    override val routerState: RouterState<*, RootComponent.Child> by router.state.toComposeState(
        lifecycle
    )

    override val messageComponent = componentFactory.createMessagesComponent(
        childContext(key = "message")
    )

    private fun createChild(config: ChildConfig, componentContext: ComponentContext) =
        when (config) {
            is ChildConfig.PhotoList -> {
                RootComponent.Child.PhotoList(
                    componentFactory.createPhotoListComponent(componentContext)
                )
            }
        }

    override fun onDeepLink(deepLink: DeepLink) {
        when (deepLink) {
            is DeepLink.DetailedPhoto -> {
                // TODO: open DetailedPhoto
            }
        }
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object PhotoList : ChildConfig
    }
}