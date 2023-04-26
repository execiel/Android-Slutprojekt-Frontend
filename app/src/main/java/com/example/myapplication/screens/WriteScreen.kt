package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun WriteScreen(
    token: String,
    navigateHome: () -> Unit,
    navigateWrite: () -> Unit,
    navigateSearch: () -> Unit,
) {

    // Title state
    val (title, setTitle) = remember {
        mutableStateOf("")
    }

    // Content state
    val (content, setContent) = remember {
        mutableStateOf("")
    }

    // Content state
    val (error, setError) = remember {
        mutableStateOf("")
    }

    Scaffold(bottomBar = {
        BottomBar("Write", navigateHome, navigateWrite, navigateSearch)
    }) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CompTitle("Write a new note")
                CompError(text = error)
                CompInput(value = title, setValue = setTitle, label = "Title of your note")
                OutlinedTextField(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(15.dp)
                        .requiredWidth(300.dp)
                        .requiredHeight(200.dp),
                    value = content,
                    onValueChange = setContent
                )
                CompButton(
                    onClick = {
                              sendPost(token, title, setTitle, content, setContent, setError)
                    },
                    label = "Submit!"
                )
            }
        }
    }
}

fun sendPost(
    token: String,
    title: String,
    setTitle: (String) -> Unit,
    content: String,
    setContent: (String) -> Unit,
    setError: (String) -> Unit
) {
    // Verify information
    if (title == "" || content == "") {
        setError("Please fill out all fields");
        return;
    }

    if (title.length > 200 || content.length > 1000) {
        setError("Title or content is to long.");
        return;
    }

    // Create body of post
    val body = mapOf("token" to token, "title" to title, "content" to content)

    apiInterface.addPost(body).enqueue(object : Callback<Void> {
        override fun onFailure(call: Call<Void>, t: Throwable) {
            setError("Connection error")
        }
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.code() == 200) {
                setTitle("")
                setContent("")
                return
            } else if (response.code() == 417) {
                setError("Bad token, please log out and back in")
                return
            }
            setError("Something went wrong")
        }
    })

}