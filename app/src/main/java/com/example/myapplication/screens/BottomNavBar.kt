package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.BottomNavItem
import com.example.myapplication.ui.theme.Peach200

@Composable
fun BottomBar(
    selectedItem: String,
    navigateHome: () -> Unit,
    navigateWrite: () -> Unit,
    navigateSettings: () -> Unit
) {
    val items = listOf(
        BottomNavItem("Home", { navigateHome() }, Icons.Filled.Home),
        BottomNavItem("Write", { navigateWrite() }, Icons.Filled.Add),
        BottomNavItem("Settings", { navigateSettings() }, Icons.Filled.Settings),
    )

    BottomNavigation(
        backgroundColor = Peach200
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = (selectedItem == item.name),
                onClick = { item.onClick() },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.name,
                    )
                },
                label = { Text(item.name) },
                enabled = true
            )
        }
    }
}
