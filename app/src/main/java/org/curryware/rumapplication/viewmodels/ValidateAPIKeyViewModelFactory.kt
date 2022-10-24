package org.curryware.rumapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.curryware.rumapplication.repositories.APIHandlerRepository
import org.curryware.rumapplication.resthandler.RestAPIHelper

class ValidateAPIKeyViewModelFactory(private val restAPIHelper: RestAPIHelper): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ValidateAPIKeyViewModel::class.java)) {
            return  ValidateAPIKeyViewModel(APIHandlerRepository(restAPIHelper)) as T
        }
        throw IllegalArgumentException("Unknown Class")
    }
}