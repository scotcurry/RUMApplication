package org.curryware.rumapplication.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.curryware.rumapplication.datadoghandler.DatadogLogger
import org.curryware.rumapplication.resthandler.SQLRestAPIHelper
import org.curryware.rumapplication.resthandler.SQLRestRetroFitBuilder
import org.curryware.rumapplication.sqlquerymodels.EmployeeDetail
import org.curryware.rumapplication.viewmodels.SQLDatabaseViewModel
import org.curryware.rumapplication.viewmodels.SQLDatabaseViewModelFactory

@Composable
fun EmployeeListScreen( navController: NavController ) {

    // GlobalRum.get().startView("EmployeeListScreen", "OrganizationScreen")
    val logger = DatadogLogger.getLogger()

    val rumCustomAttributes = mutableMapOf<String, String>()
    rumCustomAttributes["rumCustomAttributes"] = "Find this in a SQLQueryScreen Custom Attributes Section"
    // GlobalRum.get().addUserAction(RumActionType.CLICK, "Calling monitor", rumCustomAttributes)
    // GlobalRum.get().startView("EmployeeListScreen", "Employee List Screen", rumCustomAttributes)

    val sqlRestAPIHelper = SQLRestAPIHelper(SQLRestRetroFitBuilder.sqlRestAPIWorker)
    val databaseViewModel: SQLDatabaseViewModel = viewModel(factory = SQLDatabaseViewModelFactory(sqlRestAPIHelper, logger))
    databaseViewModel.getEmployeeList()
    val employeeList by databaseViewModel.employeeListReturnValue.observeAsState(initial = null)

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(8.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0xFFF518A0),
                        Color((0xFFB232BD))
                    )
                )
            )
            .verticalScroll(rememberScrollState())
    ) {
        employeeList?.employeeNameRecords?.forEach { employeeRecord ->
            EmployeeRow(employeeRow = employeeRecord, navController)
        }
    }
}

@Composable
fun EmployeeRow(employeeRow: EmployeeDetail, navController: NavController) {
    Card( modifier = Modifier
        .padding(all = 10.dp)
        .fillMaxWidth()
        .background(
            brush = Brush.horizontalGradient(
                listOf(
                    Color(0xFFF518A0),
                    Color((0xFFB232BD))
                )
            )
        )
        .clickable { navController.navigate("employeeDetailScreen/") }) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            Text(employeeRow.FirstName!!.toString(), fontSize = 15.sp)
            Text(employeeRow.LastName!!.toString(), fontSize = 20.sp)
        }
    }
}