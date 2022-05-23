package ru.mobileup.features.photo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.mobileup.features.photo.domain.Photo
import ru.mobileup.features.photo.domain.PhotoId

@Serializable
class PhotoResponse(
    @SerialName("id") val id: String,
    @SerialName("urls") val urls: PhotoUrlsResponse,
)

@Serializable
class PhotoWrapperResponse(
    @SerialName("results") val photos: List<PhotoResponse>
)

@Serializable
class PhotoUrlsResponse(
    @SerialName("small") val small: String
)

fun PhotoResponse.toDomain(): Photo {
    return Photo(
        id = PhotoId(id),
        url = urls.small
    )
}

fun PhotoWrapperResponse.toDomain(): List<Photo> {
    return photos.map { it.toDomain() }
}