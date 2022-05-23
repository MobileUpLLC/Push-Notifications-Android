package ru.mobileup.features.notification.domain

import ru.mobileup.core.platform.MobilePlatformDetector
import ru.mobileup.features.notification.data.PushTokenStorage
import timber.log.Timber

class SendPushTokenInteractor(
    private val pushTokenStorage: PushTokenStorage,
    private val mobilePlatformDetector: MobilePlatformDetector
) {

    suspend fun execute() {
        // TODO: Check isAuthorized if need
        val preferredMobilePlatform = mobilePlatformDetector.getPreferredMobilePlatform()
        preferredMobilePlatform?.let {
            val pushToken = pushTokenStorage.getPushToken(platform = it)
            val pushTokenSent = pushTokenStorage.isPushTokenSent(platform = it)

            Timber.w("pushToken = $pushToken")
            Timber.w("pushToken sent = $pushTokenSent")

            if (pushToken == null || pushTokenSent) return

            // TODO: Send pushToken on server

            pushTokenStorage.setPushTokenSent(platform = it, isSent = true)
        }
    }
}