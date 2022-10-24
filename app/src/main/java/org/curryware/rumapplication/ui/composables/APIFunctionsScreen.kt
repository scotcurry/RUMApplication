package org.curryware.rumapplication.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun APIFunctionsScreen(navController: NavController) {

    Column(modifier = Modifier
        .fillMaxWidth()) {
        Button(onClick = { navController.navigate("validateAPIScreen") }, modifier = Modifier) {
            Text(text = "Validate API Key")
        }
    }
    // Text("API Functions Screen")
}