package org.curryware.rumapplication.apimodels.organization

import com.google.gson.annotations.SerializedName


data class SamlIdpInitiatedLogin (

    @SerializedName("enabled" ) var enabled : Boolean? = null

)