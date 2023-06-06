package org.curryware.rumapplication.resthandler

import org.curryware.rumapplication.sqlquerymodels.StateSalesTaxList
import retrofit2.Response
import retrofit2.http.GET

interface SQLRestAPIWorker {

    @GET("/api/database")
    suspend fun getStateSalesTax(): Response<StateSalesTaxList>
}