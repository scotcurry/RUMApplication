package org.curryware.rumapplication.sqlquerymodels

import com.google.gson.annotations.SerializedName

    data class EmployeeDetail (

    @SerializedName("BusinessEntityID"  ) var BusinessEntityID      : Int? = null,
    @SerializedName("FirstName"         ) var FirstName             : String? = null,
    @SerializedName("LastName"          ) var LastName              : String? = null
)