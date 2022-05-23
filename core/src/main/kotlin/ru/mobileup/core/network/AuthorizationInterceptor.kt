package ru.mobileup.core.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.mobileup.core.BuildConfig

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authenticatedRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID ${BuildConfig.ACCESS_KEY}")
            .build()
        return chain.proceed(authenticatedRequest)
    }
}