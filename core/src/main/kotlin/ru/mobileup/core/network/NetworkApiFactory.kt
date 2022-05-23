package ru.mobileup.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.mobileup.core.BuildConfig
import timber.log.Timber
import java.util.concurrent.TimeUnit

@ExperimentalSerializationApi
class NetworkApiFactory(private val urlProvider: BaseUrlProvider) {

    companion object {
        private const val CONNECT_TIMEOUT_SECONDS = 30L
        private const val READ_TIMEOUT_SECONDS = 60L
        private const val WRITE_TIMEOUT_SECONDS = 60L
    }

    private val json = createJson()
    private val authorizedOkHttpClient = createOkHttpClient(authorized = true)
    private val unauthorizedOkHttpClient = createOkHttpClient(authorized = false)

    private val authorizedRetrofit = createRetrofit(authorizedOkHttpClient)
    private val unauthorizedRetrofit = createRetrofit(unauthorizedOkHttpClient)

    /**
     * Creates an api that requires authorization
     */
    inline fun <reified T : Any> createAuthorizedApi(): T = createAuthorizedApi(T::class.java)

    /**
     * Creates an API that does not require authorization
     */
    inline fun <reified T : Any> createUnauthorizedApi(): T = createUnauthorizedApi(T::class.java)

    fun <T : Any> createAuthorizedApi(clazz: Class<T>): T {
        return authorizedRetrofit.create(clazz)
    }

    fun <T : Any> createUnauthorizedApi(clazz: Class<T>): T {
        return unauthorizedRetrofit.create(clazz)
    }

    private fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(urlProvider.getUrl())
            .client(okHttpClient)
            .addCallAdapterFactory(ErrorHandlingCallAdapterFactory())
            .addConverterFactory(ErrorHandlingConverterFactory(json.asConverterFactory("application/json".toMediaType())))
            .build()
    }

    private fun createOkHttpClient(authorized: Boolean): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)

                addNetworkInterceptor(createHeaderInterceptor())

                if (authorized) {
                    addInterceptor(AuthorizationInterceptor())
                }

                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(createLoggingInterceptor())
                }
            }
            .build()
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp").d(message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun createHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept-Version", "v1")
                .build()
            chain.proceed(newRequest)
        }
    }

    private fun createJson(): Json {
        return Json {
            explicitNulls = false
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}