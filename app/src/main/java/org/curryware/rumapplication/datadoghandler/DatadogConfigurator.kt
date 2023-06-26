package org.curryware.rumapplication.datadoghandler

import android.content.Context
import android.util.Log
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.core.configuration.Credentials
import com.datadog.android.log.Logger
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.tracking.AcceptAllActivities
import com.datadog.android.rum.tracking.ActivityViewTrackingStrategy
import org.curryware.rumapplication.BuildConfig

class DatadogConfigurator {

    // TODO: These are tags that are added to the information returned.  Need to research where this
    // data actually shows up in the console.
    companion object {
        private const val ENV_TAG: String = "prod"
        private const val VARIANT_TAG: String = "0.1.0"
        private val TAG: String? = DatadogConfigurator::class.simpleName

        fun getDatadogLogger(): Logger {

            // TODO: At some point this needs to be updated to show the flow of granting consent, it

            return Logger.Builder()
                .setLoggerName("android_logger")
                .setDatadogLogsEnabled(true)
                .setLogcatLogsEnabled(true)
                .setNetworkInfoEnabled(true)
                .setBundleWithRumEnabled(true)
                .setBundleWithTraceEnabled(true)
                .build()
        }

        fun initializeDD(
            context: Context
        ) {
            if (!Datadog.isInitialized()) {
                val trackingConsent = TrackingConsent.GRANTED

                Datadog.initialize(
                    context,
                    createDatadogCredentials(),
                    createDatadogConfiguration(),
                    trackingConsent
                )
            }
        }

        private fun createDatadogCredentials(): Credentials {

            val appID = BuildConfig.ANDROID_APP_ID
            val clientToken = BuildConfig.ANDROID_CLIENT_TOKEN

            Log.i(TAG, appID)
            Log.i(TAG, clientToken)

            return Credentials(
                clientToken = BuildConfig.ANDROID_CLIENT_TOKEN,
                envName = ENV_TAG,
                variant = VARIANT_TAG,
                rumApplicationId = BuildConfig.ANDROID_APP_ID
            )
        }

        // TODO: This is hardcoded to use the US1 instance.  See useSite() below.
        private fun createDatadogConfiguration(): Configuration {

            val configBuilder = Configuration.Builder(
                logsEnabled = true,
                tracesEnabled = true,
                crashReportsEnabled = true,
                rumEnabled = true
            )
                .sampleTelemetry(100F)
                .useViewTrackingStrategy(trackingStrategy())
                .useSite(DatadogSite.US1)
                .setFirstPartyHosts(tracedHosts)
                .sampleRumSessions(95.0F)

            return configBuilder.build()
        }

        // TODO: Research required.  Because this is using Jetpack Compose
        private fun trackingStrategy(): ActivityViewTrackingStrategy {

            return ActivityViewTrackingStrategy(false, AcceptAllActivities())
        }

        // Per Intellisense - requests made to the host in the list are considered a first party source
        // and categorized as such in your RUM dashboard.  Need to flesh the code out to use another
        // site.
        private val tracedHosts = listOf(
            "datadoghq.com",
            "win2019server.curryware.org"
        )
    }
}