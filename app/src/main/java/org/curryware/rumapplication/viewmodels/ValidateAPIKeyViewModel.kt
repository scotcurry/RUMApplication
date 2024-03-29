package org.curryware.rumapplication.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datadog.android.Datadog
import com.datadog.android.log.Logger
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

        val logger = datadogLogger

        if (Datadog.isInitialized()) {
            logger.i("ValidateAPIKeyViewModel - Datadog is Initialized")
            Log.d(TAG,"fun checkDatadogAPIKey")
        }

        val additionalRUMValues = mutableMapOf<String, String>()
        additionalRUMValues["method"] = "checkDatadogAPIKey()"

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
    }

    val apiCheckReturnValue = MutableLiveData<ValidateKey>()
}