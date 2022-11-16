package org.curryware.rumapplication.datadog

import android.content.Context
import android.util.Log
import com.datadog.android.Datadog
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.core.configuration.Credentials
import com.datadog.android.log.Logger
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.GlobalRum
import com.datadog.android.rum.RumMonitor
import com.datadog.android.rum.model.ResourceEvent
import com.datadog.android.rum.tracking.AcceptAllActivities
import com.datadog.android.rum.tracking.ActivityViewTrackingStrategy
import org.curryware.rumapplication.BuildConfig

//class DatadogHandler {
//
//    fun configureDatadog(currentContext: Context): Logger {
//
//        // val clientToken = BuildConfig.ANDROID_CLIENT_TOKEN
//        // val applicationId = BuildConfig.ANDROID_APP_ID
//        val trackingConsent = TrackingConsent.GRANTED
//
//        Datadog.initialize(
//            this,
//            createDatadogCredentials(),
//            createDatadogConfiguration(),
//            trackingConsent
//        )
//
//        val monitor = RumMonitor.Builder().build()
//        GlobalRum.registerIfAbsent(monitor)
//
//        Datadog.setVerbosity(Log.INFO)
//
//        return Logger.Builder()
//            .setLoggerName("android_logger")
//            .setDatadogLogsEnabled(true)
//            .setLogcatLogsEnabled(true)
//            .setNetworkInfoEnabled(true)
//            .setBundleWithRumEnabled(true)
//            .setBundleWithTraceEnabled(true)
//            .build()
//    }
//
//    // Not much to explain here.  For now guessing about ENV and VARIANT.
//    private fun createDatadogCredentials(): Credentials {
//
//        return Credentials(
//            clientToken = BuildConfig.ANDROID_CLIENT_TOKEN,
//            envName = ENV_TAG,
//            variant = VARIANT_TAG,
//            rumApplicationId = BuildConfig.APPLICATION_ID
//        )
//    }
//
//    // This is used for setFirstPartyHosts, the intellisense is pretty good about describing what
//    // the method does.
//    private val tracedHosts = listOf(
//        "datadoghq.com"
//    )
//
//    // This code needs research.  It seems like this is a way to determine what views needs to be
//    // traced, but not clear to me yet.
//    private fun trackingStrategy(): ActivityViewTrackingStrategy {
//
//        return ActivityViewTrackingStrategy(false, AcceptAllActivities())
//    }
//
//
//    private fun createDatadogConfiguration(): Configuration {
//
//        val configBuilder = Configuration.Builder(
//            logsEnabled = true,
//            tracesEnabled = true,
//            crashReportsEnabled = true,
//            rumEnabled = true
//        )
//            .sampleTelemetry(80F)
//            .useViewTrackingStrategy(trackingStrategy())
//            .setFirstPartyHosts(tracedHosts)
//
//        return configBuilder.build()
//    }
//}