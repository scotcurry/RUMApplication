package org.curryware.rumapplication.resthandler

import android.util.Log
import android.webkit.TracingController
import androidx.compose.animation.splineBasedDecay
import com.datadog.android.core.sampling.RateBasedSampler
import com.datadog.android.okhttp.DatadogEventListener
import com.datadog.android.okhttp.DatadogInterceptor
import com.datadog.android.okhttp.trace.TracingInterceptor
import com.datadog.android.trace.TracingHeaderType
import okhttp3.OkHttpClient
import org.curryware.rumapplication.BuildConfig
import org.curryware.rumapplication.MainActivity
import org.curryware.rumapplication.datadoghandler.DatadogLogger
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SQLRestRetroFitBuilder {

    private val TAG: String? = SQLRestRetroFitBuilder::class.simpleName

    private val retrofit by lazy {

        // The first party site mapping is documented at https://docs.datadoghq.com/real_user_monitoring/connect_rum_and_traces/?tab=android
        val tracedHosts = BuildConfig.TRACED_URLS
        var hostMap = listOf("win2019.curryware.org", "datadoghq.com")
        val allHosts = tracedHosts.split(",")

        val logger = DatadogLogger.getLogger()
        logger.i("Build SQL Retrofit Builder")
        Log.i(TAG, "Build SQL Retrofit Builder")

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(DatadogInterceptor(traceSampler = RateBasedSampler(100f)))
            .addNetworkInterceptor(TracingInterceptor(traceSampler = RateBasedSampler(100f)))
            .eventListenerFactory(DatadogEventListener.Factory())
            .build()

        val apiURL = BuildConfig.CURRYWARE_URL

        httpClient.let {
            Retrofit.Builder()
                .baseUrl(apiURL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    val sqlRestAPIWorker: SQLRestAPIWorker by lazy {
        retrofit.create(SQLRestAPIWorker::class.java)
    }
}