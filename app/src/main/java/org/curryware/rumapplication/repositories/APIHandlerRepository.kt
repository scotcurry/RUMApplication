package org.curryware.rumapplication.repositories

import org.curryware.rumapplication.resthandler.RestAPIHelper

class APIHandlerRepository(private val restAPIHelper: RestAPIHelper) {

    suspend fun validateAPIKey(queryParams: Map<String, String>) = restAPIHelper.checkAPIKey(queryParams)
}