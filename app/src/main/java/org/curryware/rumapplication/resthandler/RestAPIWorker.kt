package org.curryware.rumapplication.resthandler

import org.curryware.rumapplication.apimodels.ValidateKey
import org.curryware.rumapplication.apimodels.organization.OrganizationInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface RestAPIWorker {

    @GET("/api/v1/validate")
    suspend fun validateAPIKey(@HeaderMap headers: Map<String, String>): Response<ValidateKey>

    @GET("/api/v1/org")
    suspend fun getOrganizationInfo(@HeaderMap headers: Map<String, String>): Response<OrganizationInfo>
}