package org.curryware.rumapplication.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.curryware.rumapplication.R
import org.curryware.rumapplication.datadoghandler.DatadogLogger
import org.curryware.rumapplication.resthandler.RestAPIHelper
import org.curryware.rumapplication.resthandler.RestRetroFitBuilder
import org.curryware.rumapplication.viewmodels.ValidateAPIKeyViewModel
import org.curryware.rumapplication.viewmodels.ValidateAPIKeyViewModelFactory

@Composable
fun ValidateAPIKeyScreen(navController: NavController) {

    val logger = DatadogLogger.getLogger()

    // val monitor = RumMonitor.Builder().build()
    // GlobalRum.registerIfAbsent(monitor)
    val rumCustomAttributes = mutableMapOf<String, String>()
    rumCustomAttributes["rumCustomAttributes"] = "Find this in a ValidateAPIScreen Custom Attributes Section"
    // GlobalRum.get().addUserAction(RumActionType.CLICK, "Calling monitor", rumCustomAttributes)
    // GlobalRum.get().startView("ValidateAPIScreen", "ValidateAPIKeyScreen", rumCustomAttributes)

    val imageID: Int
    val restAPIHelper = RestAPIHelper(RestRetroFitBuilder.restAPIWorker)
    val validateAPIKeyViewModel: ValidateAPIKeyViewModel = viewModel(factory = ValidateAPIKeyViewModelFactory(restAPIHelper, logger))
    validateAPIKeyViewModel.checkDatadogAPIKey()
    val apiCheckData by validateAPIKeyViewModel.apiCheckReturnValue.observeAsState(initial = null)
    val isKeyValid = apiCheckData?.valid
    imageID = if (isKeyValid != null) {
        R.drawable.ic_baseline_check_circle_24
    } else {
        R.drawable.ic_baseline_error_24
    }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("API Key Validator")
        Spacer(modifier = Modifier)
        Image(painter = painterResource(id = imageID),
            contentDescription = "Valid")
        if ((isKeyValid != null) && isKeyValid) {
            Button(onClick = { navController.navigate("validatedScreen") },
                modifier = Modifier) {
                Text(text = "Next")
            }
        }
    }

    // GlobalRum.get().stopView("ValidateAPIScreen")
}