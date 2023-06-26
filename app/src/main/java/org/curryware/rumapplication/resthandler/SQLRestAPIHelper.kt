package org.curryware.rumapplication.resthandler

class SQLRestAPIHelper(private val sqlRestAPIWorker: SQLRestAPIWorker) {

    suspend fun getStateSalesTax() = sqlRestAPIWorker.getStateSalesTax()
    suspend fun getEmployeeList() = sqlRestAPIWorker.getEmployees()
}