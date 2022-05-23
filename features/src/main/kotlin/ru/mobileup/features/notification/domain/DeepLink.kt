package ru.mobileup.features.notification.domain

import ru.mobileup.features.photo.domain.PhotoId

sealed interface DeepLink {

    class DetailedPhoto(val id: PhotoId) : DeepLink
}