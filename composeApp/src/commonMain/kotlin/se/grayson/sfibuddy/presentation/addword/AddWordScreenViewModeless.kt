package se.grayson.sfibuddy.presentation.addword

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier

@Composable
fun AddWordScreen(modifier: Modifier = Modifier) {
    AddWordScreenViewModeless(modifier)
}

@Composable
fun AddWordScreenViewModeless(modifier: Modifier = Modifier) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(

    ) {

    }
}