package org.curryware.rumapplication.resthandler

import com.datadog.android.DatadogInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.curryware.rumapplication.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestRetroFitBuilder {

    private val retrofit by lazy {

        val httpClient = OkHttpClient()
        httpClient.newBuilder().addInterceptor(DatadogInterceptor()).build()

        val apiURL = BuildConfig.API_URL

        httpClient.let {
            Retrofit.Builder()
                .baseUrl(apiURL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    val restAPIWorker: RestAPIWorker by lazy {
        retrofit.create(RestAPIWorker::class.java)
    }
}