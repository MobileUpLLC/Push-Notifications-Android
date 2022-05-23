package ru.mobileup.features.notification.domain

import ru.mobileup.core.platform.MobilePlatform
import ru.mobileup.features.notification.data.PushTokenStorage

class LogoutInteractor(private val pushTokenStorage: PushTokenStorage) {

    suspend fun execute() {
        // TODO: Check isAuthorized if need

        // TODO: Send information to the server about the removal of the token

        pushTokenStorage.setPushTokenSent(MobilePlatform.GMS, isSent = false)
        pushTokenStorage.setPushTokenSent(MobilePlatform.HMS, isSent = false)
    }
}