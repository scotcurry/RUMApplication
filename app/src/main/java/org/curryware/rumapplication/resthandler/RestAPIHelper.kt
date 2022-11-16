package org.curryware.rumapplication.resthandler

class RestAPIHelper(private val restAPIWorker: RestAPIWorker) {

    suspend fun checkAPIKey(headerParameters: Map<String, String>) = restAPIWorker.validateAPIKey(headerParameters)
}