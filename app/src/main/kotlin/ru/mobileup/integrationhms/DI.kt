package ru.mobileup.integrationhms

import ru.mobileup.core.coreModule
import ru.mobileup.features.notification.notificationModule
import ru.mobileup.features.photo.photoModule

val allModules = listOf(
    coreModule(BuildConfig.BACKEND_URL),
    photoModule,
    notificationModule(MainActivity::class.java)
)