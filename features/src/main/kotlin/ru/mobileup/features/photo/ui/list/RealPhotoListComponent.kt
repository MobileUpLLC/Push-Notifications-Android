package ru.mobileup.features.photo.ui.list

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.input.ImeAction
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.lifecycle.doOnStart
import me.aartikov.replica.keyed.KeyedReplica
import me.aartikov.replica.single.Replica
import me.aartikov.sesame.compose.form.control.InputControl
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.error_handling.safeLaunch
import ru.mobileup.core.search.createSearchComponent
import ru.mobileup.core.utils.componentCoroutineScope
import ru.mobileup.core.utils.observe
import ru.mobileup.features.notification.domain.SendPushTokenInteractor
import ru.mobileup.features.photo.domain.Photo

class RealPhotoListComponent(
    componentContext: ComponentContext,
    private val photosBySearchQueryReplica: KeyedReplica<String, List<Photo>>,
    private val randomPhotosReplica: Replica<List<Photo>>,
    errorHandler: ErrorHandler,
    sendPushTokenInteractor: SendPushTokenInteractor,
    componentFactory: ComponentFactory
) : ComponentContext by componentContext, PhotoListComponent {

    companion object {
        private const val SEARCH_QUERY_LENGTH = 100
    }

    override val searchComponent = componentFactory.createSearchComponent(
        componentContext = childContext("search_photos"),
        inputControl = InputControl(
            maxLength = SEARCH_QUERY_LENGTH,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )
    )

    private var searchQuery by searchComponent::searchQuery

    private val coroutineScope = componentCoroutineScope()

    override val searchingPhotosState by photosBySearchQueryReplica.observe(
        lifecycle,
        errorHandler,
        key = { searchQuery }
    )

    override val randomPhotosState by randomPhotosReplica.observe(
        lifecycle,
        errorHandler
    )

    override val isEmptySearchingPlaceholderVisible by derivedStateOf {
        searchComponent.inputControl.text.isEmpty()
    }

    init {
        lifecycle.doOnStart {
            coroutineScope.safeLaunch(errorHandler = errorHandler, showError = false) {
                sendPushTokenInteractor.execute()
            }
        }
    }

    override fun onRetryClick() {
        if (isEmptySearchingPlaceholderVisible) {
            randomPhotosReplica.refresh()
        } else {
            photosBySearchQueryReplica.refresh(searchQuery)
        }
    }
}
