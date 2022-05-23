package ru.mobileup.features.notification.data

import android.app.NotificationManager
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import ru.mobileup.features.R
import ru.mobileup.features.notification.domain.RemoteMessage

class PushNotifier(
    private val context: Context,
    private val activityClass: Class<*>
) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private var notificationId: Int = 1

    fun showNotification(remoteMessage: RemoteMessage) = with(context) {
        val pendingIntent = getActivity(
            this,
            notificationId++,
            Intent(this, activityClass).apply {
                putExtras(remoteMessage.data.toBundle())
            },
            FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
        )

        val notificationBuilder =
            NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(remoteMessage.notification.title)
                .setContentText(remoteMessage.notification.body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

        notificationManager.notify(notificationId++, notificationBuilder.build())
    }

    private fun Map<String, String>.toBundle(): Bundle {
        return Bundle().apply {
            forEach { putString(it.key, it.value) }
        }
    }
}