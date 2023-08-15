package org.curryware.rumapplication.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datadog.android.Datadog
import com.datadog.android.log.Logger
import kotlinx.coroutines.launch
import org.curryware.rumapplication.datadoghandler.DatadogLogger
import org.curryware.rumapplication.repositories.SQLAPIHandlerRepository
import org.curryware.rumapplication.sqlquerymodels.EmployeeList
import org.curryware.rumapplication.sqlquerymodels.StateSalesTaxList

class SQLDatabaseViewModel(private val sqlApiHandlerRepository: SQLAPIHandlerRepository,
                           private val datadogLogger: Logger): ViewModel() {

    companion object {
        private val TAG: String? = SQLDatabaseViewModel::class.simpleName
    }

    fun getSalesTaxInfo() {

        //val logger = configureDatadog()
        val logger = datadogLogger

        if (Datadog.isInitialized()) {
            logger.i("sqlApiHandlerRepository - Datadog is Initialized")
        }


        if (Datadog.isInitialized()) {
            Log.i(TAG, "Datadog is initialized - No Error")
        } else {
            Log.e(TAG, "Datadog not initialized! - With Error")
        }

        Log.i(TAG, "Launching GetSalesTaxViewModel")
        logger.i("Making API Call")
        var counter = 0
        viewModelScope.launch {
            counter++
            val response = sqlApiHandlerRepository.getSalesTaxData()
            Log.i(TAG, "Response Code: ${response.code()}")
            Log.i(TAG, "Message Body: ${response.body()}")
            Log.i(TAG, "Counter Value: $counter")
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

    fun getEmployeeList() {

        val logger = DatadogLogger.getLogger()

        var counter = 0
        viewModelScope.launch {
            counter++
            val response = sqlApiHandlerRepository.getEmployeeList()
            Log.i(TAG, "Response Code: ${response.code()}")
            Log.i(TAG, "Message Body: ${response.body()}")
            if (response.body() != null) {
                val employeeList = response.body()!!
                val listSize = employeeList.employeeNameRecords?.size
                Log.i(TAG, listSize.toString())
                employeeListReturnValue.postValue(employeeList)
                logger.i("Valid response to query")
            } else {
                logger.e("No response from SQL Server Request")
                Log.i(TAG, "Error retrieving data")
            }
        }
    }

    val employeeListReturnValue = MutableLiveData<EmployeeList>()
}