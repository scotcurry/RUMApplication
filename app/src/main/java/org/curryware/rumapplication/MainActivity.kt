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
import com.datadog.android.rum.GlobalRum
import com.datadog.android.rum.RumMonitor
import org.curryware.rumapplication.datadoghandler.DatadogConfigurator
import org.curryware.rumapplication.ui.composables.AppNavigationComponent
import org.curryware.rumapplication.ui.theme.RUMApplicationTheme

class MainActivity : ComponentActivity() {

    companion object {
        private val TAG: String? = MainActivity::class.simpleName
    }

    private val ENV_TAG: String = "prod"
    private val VARIANT_TAG: String = "v0.1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // IMPORTANT: This is how you get context.  Need this a lot.
        val activityContext = this

        // Initialize the Datadog libraries.
        DatadogConfigurator.initializeDD(activityContext)
        val logger = DatadogConfigurator.getDatadogLogger()
        val monitor = RumMonitor.Builder().build()
        GlobalRum.registerIfAbsent(monitor)

        logger.i("Initialize Datadog Libraries" )
        Log.i(TAG, "Initialize Datadog Libraries")
        if (Datadog.isInitialized()) {
            logger.i("MainActivity - Datadog Initialize")
            val globalRumString = GlobalRum.get().toString()
            logger.i("Global RUM String: $globalRumString")
            Log.i(TAG, "Global RUM String: $globalRumString")
        } else {
            logger.e("Datadog Not Initialized in MainActivity")
        }

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
