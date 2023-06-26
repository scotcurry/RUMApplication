package org.curryware.rumapplication.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.curryware.rumapplication.datadoghandler.DatadogConfigurator
import org.curryware.rumapplication.resthandler.SQLRestAPIHelper
import org.curryware.rumapplication.resthandler.SQLRestRetroFitBuilder
import org.curryware.rumapplication.sqlquerymodels.EmployeeDetail
import org.curryware.rumapplication.sqlquerymodels.EmployeeList
import org.curryware.rumapplication.viewmodels.SQLDatabaseViewModel
import org.curryware.rumapplication.viewmodels.SQLDatabaseViewModelFactory

@Composable
fun EmployeeListScreen( modifier: Modifier = Modifier ) {

    val logger = DatadogConfigurator.getDatadogLogger()

    val sqlRestAPIHelper = SQLRestAPIHelper(SQLRestRetroFitBuilder.sqlRestAPIWorker)
    val databaseViewModel: SQLDatabaseViewModel = viewModel(factory = SQLDatabaseViewModelFactory(sqlRestAPIHelper, logger))
    databaseViewModel.getEmployeeList()
    val employeeList by databaseViewModel.employeeListReturnValue.observeAsState(initial = null)
    var counter = 0

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(8.dp)
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
    ) {
        employeeList?.employeeNameRecords?.forEach { employeeRecord ->
            EmployeeRow(employeeRow = employeeRecord)
        }
    }
}

@Composable
fun EmployeeRow(employeeRow: EmployeeDetail) {
    Card( modifier = Modifier
        .padding(all = 10.dp)
        .fillMaxWidth()) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            Text(employeeRow.FirstName!!.toString(), fontSize = 15.sp)
            Text(employeeRow.LastName!!.toString(), fontSize = 20.sp)
        }
    }
}