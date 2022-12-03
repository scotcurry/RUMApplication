package org.curryware.rumapplication.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datadog.android.Datadog
import com.datadog.android.log.Logger
import com.datadog.android.rum.*
import kotlinx.coroutines.launch
import org.curryware.rumapplication.BuildConfig
import org.curryware.rumapplication.apimodels.ValidateKey
import org.curryware.rumapplication.repositories.APIHandlerRepository

class ValidateAPIKeyViewModel(private val apiHandlerRepository: APIHandlerRepository,
                              private val datadogLogger: Logger) : ViewModel() {

    companion object {
        private val TAG: String? = ValidateAPIKeyViewModel::class.simpleName
    }

    fun checkDatadogAPIKey() {

        //val logger = configureDatadog()
        val logger = datadogLogger

        if (Datadog.isInitialized()) {
            logger.i("ValidateAPIKeyViewModel - Datadog is Initialized")
        }

        // val monitor = RumMonitor.Builder().build()
        //GlobalRum.registerIfAbsent(monitor)
        val additionalRUMValues = mutableMapOf<String, String>()
        additionalRUMValues["method"] = "checkDatadogAPIKey()"
        GlobalRum.get().startView("ValidateAPIKeyViewModel", "StartView", additionalRUMValues)

        if (Datadog.isInitialized()) {
            Log.i(TAG, "Datadog is initialized - No Error")
        } else {
            Log.e(TAG, "Datadog not initialized! - With Error")
        }


        Log.i(TAG, "Launching ValidateAPIKeyViewModel")
        viewModelScope.launch {
            val headerValues = mutableMapOf<String, String>()
            headerValues["Content-Type"] = "application/json"
            headerValues["DD-API-KEY"] = BuildConfig.DD_API_KEY
            headerValues["DD-APPLICATION-KEY"] = BuildConfig.DD_REST_API_KEY
            val response = apiHandlerRepository.validateAPIKey(headerValues)
            Log.i(TAG, "Response Code: ${response.code()}")
            Log.i(TAG, "Message Body: ${response.body()}")
            if (response.body() != null) {
                val apiCheckDataBody = response.body()!!
                apiCheckReturnValue.postValue(apiCheckDataBody)
            }
        }
        GlobalRum.get().stopView("ValidateAPIKeyViewModel")
    }

    private fun configureDatadog(): Logger {

        Datadog.setVerbosity(Log.INFO)

        return Logger.Builder()
            .setLoggerName("android_logger")
            .setDatadogLogsEnabled(true)
            .setLogcatLogsEnabled(true)
            .setNetworkInfoEnabled(true)
            .build()
    }

    val apiCheckReturnValue = MutableLiveData<ValidateKey>()
}