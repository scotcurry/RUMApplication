package org.curryware.rumapplication.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun APIFunctionsScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Button(onClick = { navController.navigate("validateAPIScreen") },
            modifier = Modifier) {
            Text(text = "Validate API Key")
        }
        Button(
            onClick = { navController.navigate("organizationScreen") },
            modifier = Modifier) {
            Text(text = "Organization Details")
        }
        Button(
            onClick = { navController.navigate("UsageSummaryScreen") },
            modifier = Modifier) {
            Text(text = "Usage Summary")
        }
        Button(
            onClick = { navController.navigate(route = "SQLQueryScreen") },
            modifier = Modifier) {
            Text(text = "Sales Tax Query")
        }
    }
}