package org.curryware.rumapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.datadog.android.Datadog
import com.datadog.android.DatadogSite
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.core.configuration.Credentials
import com.datadog.android.log.Logger
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.GlobalRum
import com.datadog.android.rum.RumActionType
import com.datadog.android.rum.RumMonitor
import com.datadog.android.rum.tracking.*
import org.curryware.rumapplication.ui.composables.AppNavigationComponent
import org.curryware.rumapplication.ui.theme.RUMApplicationTheme

class MainActivity : ComponentActivity() {

    private val TAG: String = "MainActivity"
    private val ENV_TAG: String = "prod"
    private val VARIANT_TAG: String = "v0.1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val logger = configureDatadog()
        logger.i("Datadog Logger Message" )
        Log.i(TAG, "Datadog Logger Message")

        setContent {

            val navController = rememberNavController()
            RUMApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Log.i(TAG, "Calling Greeting Composable")
                    AppNavigationComponent(navController = navController)
                //Greeting("Android")
                }
            }
        }
    }

    private fun configureDatadog(): Logger {

        // val clientToken = BuildConfig.ANDROID_CLIENT_TOKEN
        // val applicationId = BuildConfig.ANDROID_APP_ID
        val trackingConsent = TrackingConsent.GRANTED

        Datadog.initialize(
            this,
            createDatadogCredentials(),
            createDatadogConfiguration(),
            trackingConsent
        )

        val monitor = RumMonitor.Builder().build()
        GlobalRum.registerIfAbsent(monitor)
        val someRandomMap = mutableMapOf<String, String>()
        someRandomMap["Key"] = "value"
        monitor.addUserAction(RumActionType.CLICK, "Calling monitor", someRandomMap)
        monitor.startView("StartView", "StartView", someRandomMap)

        Datadog.setVerbosity(Log.DEBUG)
        monitor.stopView("StartView")

        return Logger.Builder()
            .setLoggerName("android_logger")
            .setDatadogLogsEnabled(true)
            .setLogcatLogsEnabled(true)
            .setNetworkInfoEnabled(true)
            .setBundleWithRumEnabled(true)
            .setBundleWithTraceEnabled(true)
            .build()
    }

    // Not much to explain here.  For now guessing about ENV and VARIANT.
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

    // This is used for setFirstPartyHosts, the intellisense is pretty good about describing what
    // the method does.
    private val tracedHosts = listOf(
        "datadoghq.com"
    )

    // This code needs research.  It seems like this is a way to determine what views needs to be
    // traced, but not clear to me yet.
    private fun trackingStrategy(): ActivityViewTrackingStrategy {

        return ActivityViewTrackingStrategy(false, AcceptAllActivities())
    }


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

        return configBuilder.build()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RUMApplicationTheme {
        Greeting("Android")
    }
}