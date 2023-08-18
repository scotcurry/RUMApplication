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
import com.datadog.android.compose.NavigationViewTrackingEffect
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.rum.Rum
import com.datadog.android.rum.RumConfiguration
import com.datadog.android.rum.tracking.AcceptAllNavDestinations
import com.datadog.android.trace.AndroidTracer
import com.datadog.android.trace.Trace
import com.datadog.android.trace.TraceConfiguration
import io.opentracing.util.GlobalTracer
import org.curryware.rumapplication.datadoghandler.DatadogLogger

import org.curryware.rumapplication.ui.composables.AppNavigationComponent
import org.curryware.rumapplication.ui.theme.RUMApplicationTheme

class MainActivity : ComponentActivity() {

    companion object {
        private val TAG: String? = MainActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Everything is well documented in the source.
        // https://github.com/DataDog/dd-sdk-android/blob/develop/sample/kotlin/src/main/kotlin/com/datadog/android/sample/SampleApplication.kt
        val tracedHosts = listOf(
            "win2019server.curryware.org",
            "datadoghq.com"
        )
        val client_token = BuildConfig.ANDROID_CLIENT_TOKEN
        val configuration = Configuration.Builder(
            clientToken = BuildConfig.ANDROID_CLIENT_TOKEN,
            env = "prod",
            variant = BuildConfig.ANDROID_APP_ID
        )
            .setFirstPartyHosts(tracedHosts)
            .build()

        Datadog.initialize(this, configuration, TrackingConsent.GRANTED)

        val applicationID = BuildConfig.ANDROID_APP_ID
        val rumConfiguration = RumConfiguration.Builder(applicationID)
            .trackFrustrations(true)
            .trackUserInteractions()
            .setTelemetrySampleRate(100f)
            .build()
        Rum.enable(rumConfiguration)

        Log.i(TAG, "Client Token: $client_token")
        Log.i(TAG, "Application ID: $applicationID")

        val logger = DatadogLogger.getLogger()
        if (Datadog.isInitialized()) {
            Log.i(TAG,"Datadog Initialized")
            logger.i("Datadog Initialized")
        }

        // https://docs.datadoghq.com/tracing/trace_collection/dd_libraries/android/?tab=kotlin
        val tracesConfig = TraceConfiguration.Builder().build()
        Trace.enable(tracesConfig)
        val tracer = AndroidTracer.Builder().build()
        GlobalTracer.registerIfAbsent(tracer)

        logger.i("Initialize Datadog Libraries" )

        setContent {

            // Had to look this up using the source file.
            // https://github.com/Datadog/dd-sdk-android/tree/develop/integrations/dd-sdk-android-compose
            val navController = rememberNavController().apply { 
                NavigationViewTrackingEffect(navController = this, trackArguments = false, destinationPredicate = AcceptAllNavDestinations())
            }
            RUMApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Log.i(TAG, "Calling Greeting Composable")
                    AppNavigationComponent(navController = navController)
                }
            }
        }
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
