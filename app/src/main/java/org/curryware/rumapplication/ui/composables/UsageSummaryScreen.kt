package org.curryware.rumapplication.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import org.curryware.rumapplication.datadoghandler.DatadogLogger
import org.curryware.rumapplication.resthandler.RestAPIHelper
import org.curryware.rumapplication.resthandler.RestRetroFitBuilder
import org.curryware.rumapplication.viewmodels.GetUsageSummaryViewModel
import org.curryware.rumapplication.viewmodels.GetUsageSummaryViewModelFactory

@Composable
fun UsageSummaryScreen() {

    val logger = DatadogLogger.getLogger()

    val restAPIHelper = RestAPIHelper(RestRetroFitBuilder.restAPIWorker)
    val getUsageSummaryViewModel: GetUsageSummaryViewModel = viewModel(factory = GetUsageSummaryViewModelFactory(restAPIHelper, logger))
    val usageSummary = getUsageSummaryViewModel.getUsageSummary()

    val usageSummaryData by getUsageSummaryViewModel.usageSummaryValue.observeAsState(initial = null)
}