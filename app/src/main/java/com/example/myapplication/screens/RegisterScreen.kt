package com.example.myapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.*
import com.example.myapplication.networking.attemptRegister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegisterScreen(navigateLoginScreen: () -> Unit) {
    // Username state string
    val (username, setUsername) = remember {
        mutableStateOf("")
    }

    // Password state string
    val (password, setPassword) = remember {
        mutableStateOf("")
    }

    // Verify Password state string
    val (verifyPassword, setVerifyPassword) = remember {
        mutableStateOf("")
    }

    val (error, setError) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CompTitle("Register as user")
        CompError(error)
        CompInput(value = username, setValue = setUsername, label = "username")
        CompInputPassword(value = password, setValue = setPassword, label = "password")
        CompInputPassword(value = verifyPassword, setValue = setVerifyPassword, label = "verify password")
        CompButton(
            onClick = { attemptRegister(password, verifyPassword, username, navigateLoginScreen, setError) },
            label = "Register New User")
    }
}
