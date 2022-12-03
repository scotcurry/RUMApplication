package org.curryware.rumapplication.apimodels.organization

import com.google.gson.annotations.SerializedName


data class SamlAutocreateUsersDomains (

    @SerializedName("enabled" ) var enabled : Boolean?          = null,
    @SerializedName("domains" ) var domains : ArrayList<String> = arrayListOf()

)
