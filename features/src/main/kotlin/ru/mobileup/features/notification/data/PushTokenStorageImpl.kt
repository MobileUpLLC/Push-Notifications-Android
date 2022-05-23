package ru.mobileup.features.notification.data

import android.content.SharedPreferences
import androidx.core.content.edit
import ru.mobileup.core.platform.MobilePlatform
import ru.mobileup.core.platform.MobilePlatformDetector

class PushTokenStorageImpl(private val prefs: SharedPreferences) : PushTokenStorage {

    override fun isPushTokenSent(platform: MobilePlatform): Boolean {
        return prefs.getBoolean("${platform.name}_push_token_sent_key", false)
    }

    override fun setPushTokenSent(platform: MobilePlatform, isSent: Boolean) {
        prefs.edit { putBoolean("${platform.name}_push_token_sent_key", isSent) }
    }

    override fun updatePushToken(platform: MobilePlatform, token: String) {
        setPushTokenSent(platform = platform, isSent = false)
        prefs.edit { putString("${platform.name}_push_token_key", token) }
    }

    override fun getPushToken(platform: MobilePlatform): String? {
        return prefs.getString("${platform.name}_push_token_key", null)
    }
}
