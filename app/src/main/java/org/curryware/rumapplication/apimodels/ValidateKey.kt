package org.curryware.rumapplication.apimodels

import com.google.gson.annotations.SerializedName

data class ValidateKey(

    @SerializedName("valid") var valid: Boolean? = null
)
