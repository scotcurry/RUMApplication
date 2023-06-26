package org.curryware.rumapplication.resthandler

import org.curryware.rumapplication.sqlquerymodels.EmployeeList
import org.curryware.rumapplication.sqlquerymodels.StateSalesTaxList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SQLRestAPIWorker {

    @GET("/api/database")
    suspend fun getStateSalesTax(): Response<StateSalesTaxList>

    @GET("/api/employees/")
    suspend fun getEmployees(): Response<EmployeeList>
}