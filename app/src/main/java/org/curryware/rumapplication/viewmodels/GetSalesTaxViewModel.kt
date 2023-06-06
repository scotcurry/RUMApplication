package org.curryware.rumapplication.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datadog.android.Datadog
import com.datadog.android.log.Logger
import com.datadog.android.rum.GlobalRum
import com.datadog.android.rum.RumMonitor
import kotlinx.coroutines.launch
import org.curryware.rumapplication.repositories.SQLAPIHandlerRepository
import org.curryware.rumapplication.sqlquerymodels.StateSalesTaxList

class GetSalesTaxViewModel(private val sqlApiHandlerRepository: SQLAPIHandlerRepository,
                           private val datadogLogger: Logger): ViewModel() {

    companion object {
        private val TAG: String? = GetSalesTaxViewModel::class.simpleName
    }

    fun getSalesTaxInfo() {

        //val logger = configureDatadog()
        val logger = datadogLogger

        if (Datadog.isInitialized()) {
            logger.i("sqlApiHandlerRepository - Datadog is Initialized")
        }

        val monitor = RumMonitor.Builder().build()
        GlobalRum.registerIfAbsent(monitor)
        val additionalRUMValues = mutableMapOf<String, String>()
        additionalRUMValues["method"] = "getSalesTaxInfo()"
        GlobalRum.get().startView("GetSalesTaxViewModel", "StartView", additionalRUMValues)

        if (Datadog.isInitialized()) {
            Log.i(TAG, "Datadog is initialized - No Error")
        } else {
            Log.e(TAG, "Datadog not initialized! - With Error")
        }

        Log.i(TAG, "Launching GetSalesTaxViewModel")
        logger.i("Making API Call")
        viewModelScope.launch {
            val response = sqlApiHandlerRepository.getSalesTaxData()
            Log.i(TAG, "Response Code: ${response.code()}")
            Log.i(TAG, "Message Body: ${response.body()}")
            if (response.body() != null) {
                val stateSalesTax = response.body()!!
                stateTaxDataValue.postValue(stateSalesTax)
                logger.i("Valid response to query")
            } else {
                logger.e("No response from SQL Server Request")
                Log.i(TAG, "Error retrieving data")
            }
        }
    }

    val stateTaxDataValue = MutableLiveData<StateSalesTaxList>()
}