package ru.mobileup.features.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import ru.mobileup.core.message.ui.FakeMessageComponent
import ru.mobileup.core.message.ui.MessageUi
import ru.mobileup.core.theme.AppTheme
import ru.mobileup.core.utils.createFakeRouterState
import ru.mobileup.features.notification.domain.DeepLink
import ru.mobileup.features.photo.ui.list.FakePhotoListComponent
import ru.mobileup.features.photo.ui.list.PhotoListUi

@Composable
fun RootUi(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    Children(component.routerState, modifier) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.PhotoList -> PhotoListUi(instance.component)
        }
    }

    MessageUi(
        component = component.messageComponent,
        modifier = modifier,
        bottomPadding = 16.dp
    )
}

@Preview(showSystemUi = true)
@Composable
fun RootUiPreview() {
    AppTheme {
        RootUi(FakeRootComponent())
    }
}

class FakeRootComponent : RootComponent {

    override val routerState =
        createFakeRouterState(RootComponent.Child.PhotoList(FakePhotoListComponent()))

    override val messageComponent = FakeMessageComponent()

    override fun onDeepLink(deepLink: DeepLink) = Unit
}
