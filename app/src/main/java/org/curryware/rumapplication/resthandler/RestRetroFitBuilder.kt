package org.curryware.rumapplication.resthandler

import com.datadog.android.DatadogEventListener
import com.datadog.android.DatadogInterceptor
import com.datadog.android.tracing.TracingInterceptor
import com.datadog.trace.api.interceptor.TraceInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.curryware.rumapplication.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestRetroFitBuilder {

    private val retrofit by lazy {

        val tracedHosts = listOf("datadoghq.com")
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(DatadogInterceptor(tracedHosts))
            .addNetworkInterceptor(TracingInterceptor(tracedHosts))
            .eventListenerFactory(DatadogEventListener.Factory())
        val builtClient = httpClient.build()

        val apiURL = BuildConfig.API_URL

        httpClient.let {
            Retrofit.Builder()
                .baseUrl(apiURL)
                .client(builtClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }

    val restAPIWorker: RestAPIWorker by lazy {
        retrofit.create(RestAPIWorker::class.java)
    }
}