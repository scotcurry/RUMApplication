package org.curryware.rumapplication.apimodels.organization

import com.google.gson.annotations.SerializedName


data class Billing (

    @SerializedName("type"            ) var type          : String?  = null
)
