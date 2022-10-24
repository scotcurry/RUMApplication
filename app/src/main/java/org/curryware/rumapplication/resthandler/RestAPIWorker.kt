package org.curryware.rumapplication.resthandler

import org.curryware.rumapplication.apimodels.ValidateKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface RestAPIWorker {

    @GET("/api/v1/validate")
    suspend fun validateAPIKey(@HeaderMap headers: Map<String, String>): Response<ValidateKey>
}