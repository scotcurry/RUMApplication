package org.curryware.rumapplication.apimodels.organization

import com.google.gson.annotations.SerializedName


data class Saml (

    @SerializedName("enabled" ) var enabled : Boolean? = null

)
