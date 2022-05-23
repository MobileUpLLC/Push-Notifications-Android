package ru.mobileup.features.notification

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.mobileup.core.createPreferences
import ru.mobileup.features.notification.data.DeeplinkParser
import ru.mobileup.features.notification.data.PushNotifier
import ru.mobileup.features.notification.data.PushTokenStorage
import ru.mobileup.features.notification.data.PushTokenStorageImpl
import ru.mobileup.features.notification.domain.SendPushTokenInteractor

fun notificationModule(activityClass: Class<*>) = module {
    val prefsName = "notification_preferences"

    single(named(prefsName)) { createPreferences(androidContext(), prefsName) }
    single<PushTokenStorage> { PushTokenStorageImpl(get(named(prefsName))) }
    factory { SendPushTokenInteractor(get(), get()) }
    single { DeeplinkParser() }
    single { PushNotifier(androidContext(), activityClass) }
}