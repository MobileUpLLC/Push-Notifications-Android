package ru.mobileup.features.photo.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class PhotoId(val value: String) : Parcelable

data class Photo(
    val id: PhotoId,
    val url: String
)