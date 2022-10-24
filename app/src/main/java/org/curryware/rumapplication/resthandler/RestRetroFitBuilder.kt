package org.curryware.rumapplication.resthandler

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.http2.Header
import okhttp3.logging.HttpLoggingInterceptor
import org.curryware.rumapplication.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestRetroFitBuilder {

    private val retrofit by lazy {

        val httpClient = OkHttpClient()
        val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BASIC
        )


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