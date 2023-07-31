package org.curryware.rumapplication.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.datadog.android.Datadog

@Composable
fun AppNavigationComponent(navController: NavHostController) {

    val TAG = "AppNavigationComponent"
    if (Datadog.isInitialized()) {
        Log.i(TAG, "AppNavigationComponent - Datadog is Initialized")
    } else {
        Log.e(TAG, "AppNavigationComponent - Datadog NOT Initialized")
    }

    NavHost(navController = navController, startDestination = "apiFunctionScreen", modifier = Modifier
        .fillMaxWidth()) {

        composable("apiFunctionScreen") {
            APIFunctionsScreen(navController = navController)
        }

        composable("organizationScreen") {
            OrganizationScreen()
        }

        composable("validateAPIScreen") {
            ValidateAPIKeyScreen(navController)
        }

        composable("validatedScreen") {
            ValidationSuccessScreen()
        }

        composable("usageSummaryScreen") {
            UsageSummaryScreen()
        }

        composable("sqlQueryScreen") {
            SQLQueryScreen()
        }

        composable("employeeListScreen") {
            EmployeeListScreen(navController)
        }

        composable("employeeDetailScreen/{userID}") {
            EmployeeDetailScreen(navController)
        }
    }
}