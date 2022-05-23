package ru.mobileup.features.notification.data

import ru.mobileup.core.platform.MobilePlatform

interface PushTokenStorage {

    fun isPushTokenSent(platform: MobilePlatform): Boolean

    fun setPushTokenSent(platform: MobilePlatform, isSent: Boolean)

    fun getPushToken(platform: MobilePlatform): String?

    fun updatePushToken(platform: MobilePlatform, token: String)
}