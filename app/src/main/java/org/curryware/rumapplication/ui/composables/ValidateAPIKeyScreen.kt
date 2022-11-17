package org.curryware.rumapplication.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.datadog.android.rum.GlobalRum
import com.datadog.android.rum.RumErrorSource
import org.curryware.rumapplication.R
import org.curryware.rumapplication.resthandler.RestAPIHelper
import org.curryware.rumapplication.resthandler.RestRetroFitBuilder
import org.curryware.rumapplication.viewmodels.ValidateAPIKeyViewModel
import org.curryware.rumapplication.viewmodels.ValidateAPIKeyViewModelFactory

@Composable
fun ValidateAPIKeyScreen(navController: NavController) {

    val TAG: String = "ValidateAPIKeyScreen"

    val someRandomMap = mutableMapOf<String, String>()
    someRandomMap["Key"] = "value"
    GlobalRum.get().addError("Error", RumErrorSource.SOURCE, null, someRandomMap)

    var imageID = 0
    val restAPIHelper = RestAPIHelper(RestRetroFitBuilder.restAPIWorker)
    val validateAPIKeyViewModel: ValidateAPIKeyViewModel = viewModel(factory = ValidateAPIKeyViewModelFactory(restAPIHelper))
    validateAPIKeyViewModel.checkDatadogAPIKey()
    val apiCheckData by validateAPIKeyViewModel.apiCheckReturnValue.observeAsState(initial = null)
    val isKeyValid = apiCheckData?.valid
    imageID = if (isKeyValid != null) {
        R.drawable.ic_baseline_check_circle_24
    } else {
        R.drawable.ic_baseline_error_24
    }

    Row(modifier = Modifier
        .fillMaxWidth()) {
        Text("API Key Validator")
        Spacer(modifier = Modifier)
        Image(painter = painterResource(id = imageID),
            contentDescription = "Valid")
    }
}