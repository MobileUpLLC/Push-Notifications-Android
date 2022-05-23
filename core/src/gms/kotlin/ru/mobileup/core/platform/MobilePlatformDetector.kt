package ru.mobileup.core.platform

import android.content.Context
import com.google.android.gms.common.ConnectionResult.SUCCESS
import com.google.android.gms.common.GoogleApiAvailability

class MobilePlatformDetector(private val context: Context) {

    fun getPreferredMobilePlatform(): MobilePlatform? {
        val isGMSAvailableResult = GoogleApiAvailability
            .getInstance()
            .isGooglePlayServicesAvailable(context)

        return if (isGMSAvailableResult == SUCCESS) MobilePlatform.GMS else null
    }
}