package ru.mobileup.features.notification.domain

data class Notification(
    val title: String,
    val body: String
)

data class RemoteMessage(
    val notification: Notification,
    val data: Map<String, String>
)