package ru.mobileup.core.search

import com.arkivanov.decompose.ComponentContext
import me.aartikov.sesame.compose.form.control.InputControl
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.search.ui.RealSearchComponent
import ru.mobileup.core.search.ui.SearchComponent

fun ComponentFactory.createSearchComponent(
    componentContext: ComponentContext,
    inputControl: InputControl
): SearchComponent {
    return RealSearchComponent(componentContext, inputControl)
}