package org.curryware.rumapplication.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.curryware.rumapplication.datadoghandler.DatadogConfigurator

@Composable
fun ValidationSuccessScreen() {

    val context = LocalContext.current
    val logger = DatadogConfigurator.getDatadogLogger(context)
    logger.i("Showing Validation Screen")

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("Congratulations You Have a Valid API Key!")
    }
}