package org.curryware.rumapplication.sqlquerymodels

import com.google.gson.annotations.SerializedName

data class StateSalesTaxList (

    @SerializedName("stateSalesTaxList" ) var stateSalesTaxList : ArrayList<StateSalesTax>? = arrayListOf()
)
