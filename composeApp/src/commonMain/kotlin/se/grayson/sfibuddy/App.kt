package se.grayson.sfibuddy

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import se.grayson.sfibuddy.presentation.navigator.AppNavActions
import se.grayson.sfibuddy.presentation.navigator.SFIBuddyGraph

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val appNavController = AppNavActions(navController)
        SFIBuddyGraph(
            modifier = Modifier.fillMaxWidth(),
            navController = navController,
            appNavActions = appNavController
        )
    }
}

