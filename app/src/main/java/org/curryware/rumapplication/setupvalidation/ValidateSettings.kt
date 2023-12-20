package org.curryware.rumapplication.setupvalidation

import android.content.Context
import com.datadog.android.privacy.TrackingConsent
import org.curryware.rumapplication.BuildConfig
import org.curryware.rumapplication.MainActivity
import org.curryware.rumapplication.R

class ValidateSettings {

    fun validateApiProperties(): Boolean {

        val datadogApi = BuildConfig.DD_API_KEY.lowercase()
        val datadogApiCharSequence: CharSequence = datadogApi

        return Regex("[0-9a-f]{32}").matches(datadogApiCharSequence)
    }

    fun getTrackingConsent(currentActivity: MainActivity): TrackingConsent {

        val sharedPreferences = currentActivity.getPreferences(Context.MODE_PRIVATE)
        val currentValue = currentActivity.resources.getString(R.string.tracking_consent_status)
        return TrackingConsent.NOT_GRANTED
    }
}