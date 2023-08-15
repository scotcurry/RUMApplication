package org.curryware.rumapplication.ui.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import org.curryware.rumapplication.datadoghandler.DatadogLogger
import org.curryware.rumapplication.resthandler.RestAPIHelper
import org.curryware.rumapplication.resthandler.RestRetroFitBuilder
import org.curryware.rumapplication.viewmodels.GetOrganizationInfoViewModel
import org.curryware.rumapplication.viewmodels.GetOrganizationViewModelFactory

@Composable
fun OrganizationScreen() {

    // GlobalRum.get().startView("OrganizationScreen", "OrganizationScreen")
    // val context = LocalContext.current
    val logger = DatadogLogger.getLogger()

    val restAPIHelper = RestAPIHelper(RestRetroFitBuilder.restAPIWorker)
    val getOrganizationInfoViewModel: GetOrganizationInfoViewModel = viewModel(factory = GetOrganizationViewModelFactory(restAPIHelper, logger))
    getOrganizationInfoViewModel.getOrganizationInfo()

    val organizationData by getOrganizationInfoViewModel.organizationReturnValue.observeAsState(initial = null)
    val organizationName = organizationData?.orgs?.get(0)?.name

    organizationName?.also {
        Text(organizationName)
    }?:run {
        Text("Unknown")
    }
}