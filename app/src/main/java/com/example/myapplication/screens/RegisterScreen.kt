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
        CompError(error)
        CompInput(value = username, setValue = setUsername, label = "username")
        CompInput(value = password, setValue = setPassword, label = "password")
        CompInput(value = verifyPassword, setValue = setVerifyPassword, label = "verify password")
        CompButton(
            onClick = {
                        attemptRegister(password, verifyPassword, username, navigateLoginScreen, setError)
                      },
            label = "Register New User")
    }
}

// Verifies and tries to add user to database
fun attemptRegister(
    password: String,
    verifyPassword: String,
    username: String,
    navigateLoginScreen: () -> Unit,
    setError: (String) -> Unit
) {
    // Check if any field is empty
    if(password == "" || verifyPassword == "" || username == "")
        return setError("You need to fill out all fields")

    // Check that passwords match
    if(password != verifyPassword)
        return setError("Passwords don't match")

    // Send information to backend
    val body = mapOf("username" to username, "password" to password)

    apiInterface.registerUser(body).enqueue( object : Callback<Void> {
           override fun onFailure(call: Call<Void>, t: Throwable) {
               setError("Connection error")
           }
           override fun onResponse(call: Call<Void>, response: Response<Void>) {
               if (response.code() == 200) {
                   navigateLoginScreen()
                   return
               }
               setError("User already exists");
               return
           }
       } )
}
