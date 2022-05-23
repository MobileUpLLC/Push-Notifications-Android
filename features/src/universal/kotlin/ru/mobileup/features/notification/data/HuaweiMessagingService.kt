package ru.mobileup.features.notification.data

import com.huawei.hms.push.HmsMessageService
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

class HuaweiMessagingService : HmsMessageService(), KoinComponent {

    private val pushTokenStorage: PushTokenStorage by inject()
    private val pushNotifier: PushNotifier by inject()
    private val sendPushTokenInteractor: SendPushTokenInteractor by inject()
    private val appCoroutineScope: CoroutineScope by inject()
    private val errorHandler: ErrorHandler by inject()
    private val mobilePlatformDetector: MobilePlatformDetector by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        pushTokenStorage.updatePushToken(platform = MobilePlatform.HMS, token = token)

        if (mobilePlatformDetector.getPreferredMobilePlatform() == MobilePlatform.HMS) {
            Timber.w("HuaweiMessagingService: Received token = $token")
            appCoroutineScope.safeLaunch(errorHandler = errorHandler, showError = false) {
                sendPushTokenInteractor.execute()
            }
        }
    }

    override fun onMessageReceived(remoteMessage: com.huawei.hms.push.RemoteMessage) {
        if (mobilePlatformDetector.getPreferredMobilePlatform() == MobilePlatform.HMS) {
            pushNotifier.showNotification(
                RemoteMessage(
                    Notification(
                        remoteMessage.notification?.title ?: "",
                        remoteMessage.notification?.body ?: ""
                    ),
                    remoteMessage.dataOfMap
                )
            )
        }
    }

    override fun getKoin() = application.koin
}