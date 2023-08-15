package org.curryware.rumapplication.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datadog.android.Datadog
import com.datadog.android.log.Logger
import kotlinx.coroutines.launch
import org.curryware.rumapplication.BuildConfig
import org.curryware.rumapplication.apimodels.usagesummary.UsageSummary
import org.curryware.rumapplication.repositories.APIHandlerRepository
import java.time.LocalDate

class GetUsageSummaryViewModel(private val apiHandlerRepository: APIHandlerRepository,
                               private val datadogLogger: Logger) : ViewModel() {

    companion object {
        private val TAG: String? = GetUsageSummaryViewModel::class.simpleName
    }

    fun getUsageSummary() {
        //val logger = configureDatadog()
        val logger = datadogLogger

        if (Datadog.isInitialized()) {
            logger.i("GetUsageSummaryViewModel - Datadog is Initialized")
        }

        // val monitor = RumMonitor.Builder().build()
        //GlobalRum.registerIfAbsent(monitor)
        val additionalRUMValues = mutableMapOf<String, String>()
        additionalRUMValues["method"] = "checkDatadogAPIKey()"
        // GlobalRum.get().startView("GetUsageSummaryViewModel", "StartView", additionalRUMValues)

        if (Datadog.isInitialized()) {
            Log.i(TAG, "Datadog is initialized - No Error")
        } else {
            Log.e(TAG, "Datadog not initialized! - With Error")
        }


        Log.i(TAG, "Launching ValidateAPIKeyViewModel")
        viewModelScope.launch {

            val lastMonthString = getLastMonthString()
            val queryValues = mutableMapOf<String, String>()
            queryValues["start_month"] = lastMonthString

            val headerValues = mutableMapOf<String, String>()
            headerValues["Content-Type"] = "application/json"
            headerValues["DD-API-KEY"] = BuildConfig.DD_API_KEY
            headerValues["DD-APPLICATION-KEY"] = BuildConfig.DD_REST_API_KEY

            try {
                val response = apiHandlerRepository.getUsageSummary(headerValues, queryValues)
                Log.i(TAG, "Response Code: ${response.code()}")
                Log.i(TAG, "Response Body: ${response.body()}")
                if (response.body() != null) {
                    val usageSummaryBody = response.body()!!
                    val usageEntries = buildUsagePropertyList(usageSummaryBody)
                    val usageValues = getUsageValues(usageEntries, usageSummaryBody)
                    usageSummaryValue.postValue(usageSummaryBody)
                }
            } catch(generalException: java.lang.Exception) {
                generalException.localizedMessage?.let { Log.e(TAG, it) }
            }
        }
    }

    private fun getUsageValues(properties: MutableMap<String, String>, usageSummary: UsageSummary): MutableMap<String, Int> {

        val allKeys = properties.keys
        val usageMetrics: MutableMap<String, Int> = mutableMapOf<String, Int>()

        allKeys.forEach {
            val dataType = properties[it]
            println("$it - $dataType")
        }

        return usageMetrics
    }

    private fun buildUsagePropertyList(usageSummary: UsageSummary): MutableMap<String, String> {

        val keyList = mutableMapOf<String, String>()

        usageSummary::class.java.declaredFields.forEach {
            keyList[it.name] = it.type.simpleName
        }
        return keyList
    }

    private fun getLastMonthString(): String {

        val currentDate = LocalDate.now()
        val currentMonth = currentDate.month.value
        val currentYear = currentDate.year
        var lastMonth = 1
        var queryYear = 2023
        if (currentMonth == 1) {
            lastMonth = 12
            queryYear = currentYear - 1
        } else {
            lastMonth = currentMonth - 1
            queryYear = currentYear
        }

        var lastMonthString = "12"
        lastMonthString = if (lastMonth < 10) {
            "0$lastMonth"
        } else {
            lastMonth.toString()
        }

        Log.i(TAG, "Query Year: ${queryYear}")
        Log.i(TAG, "Last Month String: $lastMonthString")
        Log.i(TAG, "Return String: $queryYear-$lastMonthString")
        return "$queryYear-$lastMonthString"
    }

    val usageSummaryValue = MutableLiveData<UsageSummary>()
}