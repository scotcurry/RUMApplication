package org.curryware.rumapplication.resthandler

import org.curryware.rumapplication.apimodels.ValidateKey
import org.curryware.rumapplication.apimodels.organization.OrganizationInfo
import org.curryware.rumapplication.apimodels.usagesummary.UsageSummary
import org.curryware.rumapplication.sqlquerymodels.StateSalesTax
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface RestAPIWorker {

    @GET("/api/v1/validate")
    suspend fun validateAPIKey(@HeaderMap headers: Map<String, String>): Response<ValidateKey>

    @GET("/api/v1/org")
    suspend fun getOrganizationInfo(@HeaderMap headers: Map<String, String>): Response<OrganizationInfo>

    @GET("/api/v1/usage/summary")
    suspend fun getUsageSummary(@HeaderMap headers: Map<String, String>, @QueryMap queryMap: Map<String, String>): Response<UsageSummary>

    @GET("/api/database")
    suspend fun getSQLServerQueryData(@Url url: String): Response<List<StateSalesTax>>
}