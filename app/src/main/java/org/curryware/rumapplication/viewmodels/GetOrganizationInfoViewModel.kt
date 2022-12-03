package org.curryware.rumapplication.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datadog.android.log.Logger
import kotlinx.coroutines.launch
import org.curryware.rumapplication.BuildConfig
import org.curryware.rumapplication.MainActivity
import org.curryware.rumapplication.apimodels.organization.OrganizationInfo
import org.curryware.rumapplication.repositories.APIHandlerRepository

class GetOrganizationInfoViewModel(private val apiHandlerRepository: APIHandlerRepository,
                                   private val datadogLogger: Logger) : ViewModel() {

    companion object {
        private val TAG: String? = GetOrganizationInfoViewModel::class.simpleName
    }

    fun getOrganizationInfo() {
        viewModelScope.launch {
            val headerValues = mutableMapOf<String, String>()
            headerValues["Content-Type"] = "application/json"
            headerValues["DD-API-KEY"] = BuildConfig.DD_API_KEY
            headerValues["DD-APPLICATION-KEY"] = BuildConfig.DD_REST_API_KEY
            val response = apiHandlerRepository.getOrganizationInfo(headerValues)
            Log.i(TAG, "Response Code: ${response.code()}")
            Log.i(TAG, "Message Body: ${response.body()}")
            if (response.body() != null) {
                val organizationDataBody = response.body()!!
                organizationReturnValue.postValue(organizationDataBody)
            }
        }
    }

    val organizationReturnValue = MutableLiveData<OrganizationInfo>()
}