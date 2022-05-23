package ru.mobileup.core.search.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.aartikov.sesame.compose.form.control.InputControl
import ru.mobileup.core.utils.componentCoroutineScope

class RealSearchComponent(
    componentContext: ComponentContext,
    override val inputControl: InputControl
) : ComponentContext by componentContext, SearchComponent {

    companion object {
        private const val DEBOUNCE_TIME_MS = 1000L
    }

    private val coroutineScope = componentCoroutineScope()

    override var searchQuery by mutableStateOf("")

    init {
        lifecycle.doOnCreate {
            snapshotFlow {
                inputControl.text
            }.debounce { newText ->
                if (newText.isNotEmpty()) DEBOUNCE_TIME_MS else 0
            }.onEach { newText ->
                searchQuery = newText
            }.launchIn(coroutineScope)
        }
    }
}