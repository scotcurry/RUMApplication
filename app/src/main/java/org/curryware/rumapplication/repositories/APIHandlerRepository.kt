package org.curryware.rumapplication.repositories

import org.curryware.rumapplication.resthandler.RestAPIHelper

class APIHandlerRepository(private val restAPIHelper: RestAPIHelper) {

    suspend fun validateAPIKey(headerParams: Map<String, String>) = restAPIHelper.checkAPIKey(headerParams)
    suspend fun getOrganizationInfo(headerParams: Map<String, String>) = restAPIHelper.getOrganizationInfo(headerParams)
    suspend fun getUsageSummary(headerParams: Map<String, String>, queryParams: Map<String, String>) = restAPIHelper.getUsageSummary(headerParams, queryParams)
}