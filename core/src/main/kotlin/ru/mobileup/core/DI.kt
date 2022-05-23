package ru.mobileup.core

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import me.aartikov.replica.client.ReplicaClient
import me.aartikov.replica.devtools.ReplicaDevTools
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.mobileup.core.error_handling.ErrorHandler
import ru.mobileup.core.message.data.MessageService
import ru.mobileup.core.message.data.MessageServiceImpl
import ru.mobileup.core.network.BaseUrlProvider
import ru.mobileup.core.network.NetworkApiFactory
import ru.mobileup.core.network.RealBaseUrlProvider
import ru.mobileup.core.platform.MobilePlatformDetector

fun coreModule(backendUrl: String) = module {
    single { CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate) }
    single<BaseUrlProvider> { RealBaseUrlProvider(backendUrl) }
    single { ReplicaClient() }
    single { ReplicaDevTools(get(), androidContext()) }
    single<MessageService> { MessageServiceImpl() }
    single { ErrorHandler(get()) }
    single { NetworkApiFactory(get()) }
    single { MobilePlatformDetector(androidContext()) }
}

fun createPreferences(context: Context, prefsName: String): SharedPreferences {
    return context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
}