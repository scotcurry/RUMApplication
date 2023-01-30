package org.curryware.rumapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.datadog.android.log.Logger
import org.curryware.rumapplication.repositories.APIHandlerRepository
import org.curryware.rumapplication.resthandler.RestAPIHelper

class GetUsageSummaryViewModelFactory(private val restAPIHelper: RestAPIHelper,
                                      private val datadogLogger: Logger): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetUsageSummaryViewModel::class.java)) {
            return  GetUsageSummaryViewModel(APIHandlerRepository(restAPIHelper), datadogLogger) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }
                                      }