package org.curryware.rumapplication.sqlquerymodels

import com.google.gson.annotations.SerializedName

data class StateSalesTax (

    @SerializedName("Name"      ) var Name      : String? = null,
    @SerializedName("TaxRate"   ) var TaxRate   : Double? = null,
    @SerializedName("StateName" ) var StateName : String? = null
)