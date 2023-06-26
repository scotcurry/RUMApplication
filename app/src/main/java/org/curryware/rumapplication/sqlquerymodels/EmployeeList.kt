package org.curryware.rumapplication.sqlquerymodels

import com.google.gson.annotations.SerializedName

data class EmployeeList (

    @SerializedName("employeeNameRecords" ) var employeeNameRecords : ArrayList<EmployeeDetail>? = arrayListOf()
)