package org.curryware.rumapplication.repositories

import androidx.lifecycle.ViewModel
import org.curryware.rumapplication.resthandler.SQLRestAPIHelper
import java.util.logging.Logger

class SQLAPIHandlerRepository(private val sqlRestAPIHelper: SQLRestAPIHelper): ViewModel() {

    suspend fun getSalesTaxData() = sqlRestAPIHelper.getStateSalesTax()
}