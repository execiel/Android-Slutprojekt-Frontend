package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen(
    token: String,
    navigateHome: () -> Unit,
    navigateWrite: () -> Unit,
    navigateSearch: () -> Unit,
    navigateSettings: () -> Unit
) {
    Scaffold(bottomBar = {
        BottomBar("Settings", navigateHome, navigateWrite, navigateSearch, navigateSettings)
    }) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Settings...")
            }
        }
    }
}
