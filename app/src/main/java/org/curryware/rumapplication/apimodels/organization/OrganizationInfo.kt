package org.curryware.rumapplication.apimodels.organization

import com.google.gson.annotations.SerializedName

data class OrganizationInfo (

    @SerializedName("orgs" ) var orgs : ArrayList<Orgs> = arrayListOf()

)