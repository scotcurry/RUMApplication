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
import org.curryware.rumapplication.datadoghandler.DatadogLogger
import org.curryware.rumapplication.resthandler.SQLRestAPIHelper
import org.curryware.rumapplication.resthandler.SQLRestRetroFitBuilder
import org.curryware.rumapplication.sqlquerymodels.StateSalesTax
import org.curryware.rumapplication.viewmodels.SQLDatabaseViewModel
import org.curryware.rumapplication.viewmodels.SQLDatabaseViewModelFactory

@Composable
fun SQLQueryScreen( modifier: Modifier = Modifier ) {

    // GlobalRum.get().startView("OrganizationScreen", "OrganizationScreen")
    val logger = DatadogLogger.getLogger()

    val rumCustomAttributes = mutableMapOf<String, String>()
    rumCustomAttributes["rumCustomAttributes"] = "Find this in a SQLQueryScreen Custom Attributes Section"
    // GlobalRum.get().addUserAction(RumActionType.CLICK, "Calling monitor", rumCustomAttributes)
    // GlobalRum.get().startView("SQLQueryScreen", "SQLQueryScreen", rumCustomAttributes)

    val sqlRestAPIHelper = SQLRestAPIHelper(SQLRestRetroFitBuilder.sqlRestAPIWorker)
    val databaseViewModel: SQLDatabaseViewModel = viewModel(factory = SQLDatabaseViewModelFactory(sqlRestAPIHelper, logger))
    databaseViewModel.getSalesTaxInfo()

    val salesTaxData by databaseViewModel.stateTaxDataValue.observeAsState(initial = null)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(8.dp)
            .background(backgroundColor)
            .verticalScroll(rememberScrollState())
    ) {
        salesTaxData?.stateSalesTaxList?.forEach { stateSalesTax ->
            StateTaxRow(stateSalesTax = stateSalesTax)
        }
    }
}

@Composable
fun StateTaxRow(stateSalesTax: StateSalesTax) {
    Card(modifier = Modifier
        .padding(all = 10.dp)
        .fillMaxWidth()) {
        Column(modifier = Modifier.padding(all = 10.dp)) {
            Text(stateSalesTax.StateName!!, fontSize = 20.sp)
            Text(stateSalesTax.TaxRate!!.toString())
        }
    }
}