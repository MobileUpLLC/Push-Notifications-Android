package ru.mobileup.core.platform

import android.content.Context

class MobilePlatformDetector(private val context: Context) {
    private val mobilePlatformPriorities = listOf(MobilePlatform.GMS, MobilePlatform.HMS)

    fun getPreferredMobilePlatform(): MobilePlatform? {
        return mobilePlatformPriorities.firstOrNull { it.isAvailable(context) }
    }

    private fun MobilePlatform.isAvailable(context: Context) = when (this) {
        MobilePlatform.GMS -> com.google.android.gms.common.GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(context) == com.google.android.gms.common.ConnectionResult.SUCCESS

        MobilePlatform.HMS -> com.huawei.hms.api.HuaweiApiAvailability.getInstance()
            .isHuaweiMobileServicesAvailable(context) == com.huawei.hms.api.ConnectionResult.SUCCESS
    }
}