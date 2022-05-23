package ru.mobileup.features.notification.data

import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.error_handling.safeLaunch
import ru.mobileup.core.koin
import ru.mobileup.core.platform.MobilePlatform
import ru.mobileup.core.platform.MobilePlatformDetector
import ru.mobileup.features.notification.domain.Notification
import ru.mobileup.features.notification.domain.RemoteMessage
import ru.mobileup.features.notification.domain.SendPushTokenInteractor
import timber.log.Timber

class FirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    private val pushTokenStorage: PushTokenStorage by inject()
    private val pushNotifier: PushNotifier by inject()
    private val sendPushTokenInteractor: SendPushTokenInteractor by inject()
    private val appCoroutineScope: CoroutineScope by inject()
    private val errorHandler: ErrorHandler by inject()
    private val mobilePlatformDetector: MobilePlatformDetector by inject()

    override fun onNewToken(token: String) {
        pushTokenStorage.updatePushToken(platform = MobilePlatform.GMS, token = token)

        if (mobilePlatformDetector.getPreferredMobilePlatform() == MobilePlatform.GMS) {
            Timber.w("FirebaseMessagingService: Received token = $token")
            appCoroutineScope.safeLaunch(errorHandler = errorHandler, showError = false) {
                sendPushTokenInteractor.execute()
            }
        }
    }

    override fun onMessageReceived(remoteMessage: com.google.firebase.messaging.RemoteMessage) {
        if (mobilePlatformDetector.getPreferredMobilePlatform() == MobilePlatform.GMS) {
            pushNotifier.showNotification(
                RemoteMessage(
                    Notification(
                        remoteMessage.notification?.title ?: "",
                        remoteMessage.notification?.body ?: ""
                    ),
                    remoteMessage.data
                )
            )
        }
    }

    override fun getKoin() = application.koin
}