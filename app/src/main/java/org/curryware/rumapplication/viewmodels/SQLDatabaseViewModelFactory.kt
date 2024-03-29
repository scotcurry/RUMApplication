package org.curryware.rumapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.datadog.android.log.Logger
import org.curryware.rumapplication.repositories.SQLAPIHandlerRepository
import org.curryware.rumapplication.resthandler.SQLRestAPIHelper

class SQLDatabaseViewModelFactory(private val sqlRestAPIHelper: SQLRestAPIHelper,
                                  private val datadogLogger: Logger): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SQLDatabaseViewModel::class.java)) {
            return  SQLDatabaseViewModel(SQLAPIHandlerRepository(sqlRestAPIHelper), datadogLogger) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }
}