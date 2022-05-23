package ru.mobileup.features.notification.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import ru.mobileup.features.R

fun createNotificationChannels(context: Context) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // create at least one (default) channel
    createNotificationChannel(
        notificationManager = notificationManager,
        channelId = context.getString(R.string.notification_channel_id),
        channelName = context.getString(R.string.notification_channel_name),
        channelDescription = context.getString(R.string.notification_channel_description)
    )
}

private fun createNotificationChannel(
    notificationManager: NotificationManager,
    channelId: String,
    channelName: String,
    channelDescription: String
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            description = channelDescription
            notificationManager.createNotificationChannel(this)
        }
    }
}