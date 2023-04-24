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
import com.example.myapplication.networking.objects.LoginResult
import com.example.myapplication.ui.theme.Teal200
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
            text = "Blabbr",
            fontSize = 32.sp,
            color = Teal200
        )
        CompError(text = error)
        CompInput(value = username, setValue = setUsername, label = "username")
        CompInput(value = password, setValue = setPassword, label = "password")
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

fun attemptLogin(
    username: String,
    password: String,
    setError: (String) -> Unit,
    store: UserStore,
    navigateHomeScreen: () -> Unit
) {
    // Check if any field is empty
    if(password == "" || username == "")
        setError("You need to fill out all fields")

    // Send information to backend
    val body = mapOf("username" to username, "password" to password)


    apiInterface.loginUser(body).enqueue(object : Callback<LoginResult> {
        override fun onFailure(call: Call<LoginResult>, t: Throwable) {
            setError("Can't connect to server")
        }
        override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
            // Make sure response body isn't null
            val res = response.body() ?: return;

            // If connection worked but status is wrong
            if (res.status != "ok") {
                setError("Incorrect username or password")
                return;
            }

            // Store token If everything went ok
            CoroutineScope(Dispatchers.IO).launch {
                store.saveToken(res.token.toString())
            }

            // Navigate to home screen
            navigateHomeScreen()
        }
    } )

}
