package ru.mobileup.features.root.ui

import com.arkivanov.decompose.router.RouterState
import ru.mobileup.core.message.ui.MessageComponent
import ru.mobileup.features.notification.domain.DeepLink
import ru.mobileup.features.photo.ui.list.PhotoListComponent

interface RootComponent {

    val routerState: RouterState<*, Child>

    val messageComponent: MessageComponent

    fun onDeepLink(deepLink: DeepLink)

    sealed interface Child {
        class PhotoList(val component: PhotoListComponent) : Child
    }
}