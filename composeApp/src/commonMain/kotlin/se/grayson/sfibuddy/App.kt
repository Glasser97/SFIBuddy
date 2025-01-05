package se.grayson.sfibuddy

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import se.grayson.sfibuddy.presentation.home.AppNavActions
import se.grayson.sfibuddy.presentation.home.SFIBuddyGraph

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

