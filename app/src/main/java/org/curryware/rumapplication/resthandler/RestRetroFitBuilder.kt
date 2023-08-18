package org.curryware.rumapplication.resthandler

import com.datadog.android.okhttp.DatadogEventListener
import com.datadog.android.okhttp.DatadogInterceptor
import com.datadog.android.okhttp.trace.TracingInterceptor
import okhttp3.OkHttpClient
import org.curryware.rumapplication.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestRetroFitBuilder {

    private val retrofit by lazy {

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(DatadogInterceptor("datadoghq.com"))
            .addNetworkInterceptor(TracingInterceptor("datadoghq.com"))
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