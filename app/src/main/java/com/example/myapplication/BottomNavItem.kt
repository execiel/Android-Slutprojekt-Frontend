package com.example.myapplication

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name: String,
    val onClick: () -> Unit,
    val icon: ImageVector
)
