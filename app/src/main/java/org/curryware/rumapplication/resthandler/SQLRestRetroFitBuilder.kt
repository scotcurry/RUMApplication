package org.curryware.rumapplication.resthandler

import com.datadog.android.DatadogEventListener
import com.datadog.android.DatadogInterceptor
import com.datadog.android.tracing.TracingInterceptor
import okhttp3.OkHttpClient
import org.curryware.rumapplication.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SQLRestRetroFitBuilder {

    private val retrofit by lazy {

        val tracedHosts = listOf("curryware.org")
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(DatadogInterceptor(tracedHosts))
            .addNetworkInterceptor(TracingInterceptor(tracedHosts))
            .eventListenerFactory(DatadogEventListener.Factory())
        val builtClient = httpClient.build()

        val apiURL = BuildConfig.CURRYWARE_URL

        httpClient.let {
            Retrofit.Builder()
                .baseUrl(apiURL)
                .client(builtClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    val sqlRestAPIWorker: SQLRestAPIWorker by lazy {
        retrofit.create(SQLRestAPIWorker::class.java)
    }
}