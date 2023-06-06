package org.curryware.rumapplication.sqlquerymodels

import com.google.gson.annotations.SerializedName
import org.curryware.rumapplication.apimodels.organization.Orgs

data class StateSalesTaxList(

    @SerializedName("stateSalesTaxList" ) var stateSalesTaxList : ArrayList<StateSalesTax>? = arrayListOf()
)
