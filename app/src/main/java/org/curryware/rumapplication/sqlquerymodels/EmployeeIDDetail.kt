package org.curryware.rumapplication.sqlquerymodels

import com.google.gson.annotations.SerializedName

class EmployeeIDDetail {

    data class EmployeeIDDetail (

        @SerializedName("BusinessEntityID"  ) var BusinessEntityID      : Int? = null,
        @SerializedName("TaxRate"           ) var FirstName             : String? = null,
        @SerializedName("StateName"         ) var LastName              : String? = null,
        @SerializedName("EmailAddress"      ) var EmailAddress          : String? = null,
        @SerializedName("PhoneNumber"       ) var PhoneNumber           : String? = null
    )
}