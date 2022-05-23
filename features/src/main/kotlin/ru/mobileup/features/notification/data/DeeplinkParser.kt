package ru.mobileup.features.notification.data

import android.content.Intent
import android.os.Bundle
import ru.mobileup.features.notification.domain.DeepLink
import ru.mobileup.features.photo.domain.PhotoId

class DeeplinkParser {

    enum class NotificationType(val value: String) {
        OpenDetailedPhoto("NEW_PHOTO")
    }

    companion object {
        const val NOTIFICATION_TYPE_KEY = "notificationType"
        const val OBJECT_ID_KEY = "objectId"
    }

    fun getDeeplink(intent: Intent?): DeepLink? {
        return getNotificationDeeplink(intent)
    }

    private fun getNotificationDeeplink(intent: Intent?): DeepLink? = with(intent?.extras) {
        if (this == null) return null

        val type = NotificationType
            .values()
            .find { it.value == getString(NOTIFICATION_TYPE_KEY) }
            ?: return null

        return@with when (type) {
            NotificationType.OpenDetailedPhoto -> {
                val id = getPhotoId() ?: return null
                DeepLink.DetailedPhoto(id)
            }
        }
    }

    private fun Bundle.getPhotoId(): PhotoId? {
        return getString(OBJECT_ID_KEY)
            ?.let { PhotoId(it) }
    }
}