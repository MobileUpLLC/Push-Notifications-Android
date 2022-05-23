package ru.mobileup.features.photo

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.get
import org.koin.dsl.module
import ru.mobileup.core.ComponentFactory
import ru.mobileup.core.network.NetworkApiFactory
import ru.mobileup.features.photo.data.PhotoApi
import ru.mobileup.features.photo.data.PhotoRepository
import ru.mobileup.features.photo.data.PhotoRepositoryImpl
import ru.mobileup.features.photo.ui.list.PhotoListComponent
import ru.mobileup.features.photo.ui.list.RealPhotoListComponent

val photoModule = module {
    single<PhotoApi> { get<NetworkApiFactory>().createAuthorizedApi() }
    single<PhotoRepository> { PhotoRepositoryImpl(get(), get()) }
}

fun ComponentFactory.createPhotoListComponent(
    componentContext: ComponentContext
): PhotoListComponent {
    val photosBySearchQueryReplica = get<PhotoRepository>().photosBySearchQueryReplica
    val randomPhotosReplica = get<PhotoRepository>().randomPhotosReplica
    return RealPhotoListComponent(componentContext, photosBySearchQueryReplica, randomPhotosReplica, get(), get(), get())
}