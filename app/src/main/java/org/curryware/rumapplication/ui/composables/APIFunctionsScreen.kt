package org.curryware.rumapplication.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()) {
            Text(text = "Validate API Key")
        }
        Button(
            onClick = { navController.navigate("organizationScreen") },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()) {
            Text(text = "Organization Details")
        }
        Button(
            onClick = { navController.navigate("UsageSummaryScreen") },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()) {
            Text(text = "Usage Summary")
        }
        Button(
            onClick = { navController.navigate(route = "SQLQueryScreen") },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()) {
            Text(text = "Sales Tax Query")
        }

        Button(
            onClick = { navController.navigate("employeeListScreen") },
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()) {
            Text(text = "Employee List")
        }
    }
}