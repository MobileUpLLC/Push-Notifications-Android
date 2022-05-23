package ru.mobileup.features.photo.ui.list

import me.aartikov.replica.single.Loadable
import ru.mobileup.core.search.ui.SearchComponent
import ru.mobileup.features.photo.domain.Photo

interface PhotoListComponent {

    val searchComponent: SearchComponent

    val randomPhotosState: Loadable<List<Photo>>

    val searchingPhotosState: Loadable<List<Photo>>

    val isEmptySearchingPlaceholderVisible: Boolean

    fun onRetryClick()
}