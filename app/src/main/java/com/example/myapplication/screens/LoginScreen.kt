package com.example.myapplication.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.*
import com.example.myapplication.networking.attemptLogin
import com.example.myapplication.networking.objects.LoginResult
import com.example.myapplication.ui.theme.Green200
import com.example.myapplication.ui.theme.Peach200
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navigateRegisterScreen: () -> Unit, store: UserStore, navigateHomeScreen: () -> Unit) {
    val tokenText = store.getAccessToken.collectAsState(initial = "")
    // Username state string
    val (username, setUsername) = remember {
        mutableStateOf("")
    }

    // Password state string
    val (password, setPassword) = remember {
        mutableStateOf("")
    }

    //
    val (error, setError) = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(25.dp),
            text = "Notify",
            fontSize = 32.sp,
            color = Peach200
        )
        Text(
            "Cloud based note-taking for android",
        )
        CompError(text = error)
        CompInput(value = username, setValue = setUsername, label = "username")
        CompInputPassword(value = password, setValue = setPassword, label = "password")
        CompButton(
            onClick = {
                attemptLogin(
                    username,
                    password,
                    setError,
                    store,
                    navigateHomeScreen
                ) },
            label = "Login")
        CompLink(onClick = { navigateRegisterScreen() })
    }
}

