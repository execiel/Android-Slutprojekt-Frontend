package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.myapplication.*
import com.example.myapplication.networking.deletePost
import com.example.myapplication.networking.editPost
import com.example.myapplication.networking.objects.PostItem
import com.example.myapplication.networking.objects.PostResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun HomeScreen(
    token: String,
    navigateHome: () -> Unit,
    navigateWrite: () -> Unit,
    navigateSearch: () -> Unit,
    navigateSettings: () -> Unit,
    ) {

    // TODO: Move to a view model?
    val (posts, setPosts) = remember {
        mutableStateOf(listOf(PostItem(title = "You haven't taken any notes", content = "", id = "w")));
    }

    // Popup state
    val (showPopup, setShowPopup) = remember {
        mutableStateOf(false);
    }

    // popup title state
    val (popupTitle, setPopupTitle) = remember {
        mutableStateOf("");
    }

    // popup title state
    val (popupContent, setPopupContent) = remember {
        mutableStateOf("");
    }

    // Stores current id for edit popup
    val (currentId, setCurrentId) = remember {
        mutableStateOf("");
    }

    // Stores current id for edit popup
    val (error, setError) = remember {
        mutableStateOf("");
    }

    getPosts(token, setPosts);

    Scaffold(bottomBar = {
        BottomBar("Home", navigateHome, navigateWrite, navigateSearch, navigateSettings)
    }) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) {
            if (showPopup) {
                Popup (
                    alignment = Alignment.Center,
                    onDismissRequest = { setShowPopup(false) },
                    properties = PopupProperties(focusable = true, excludeFromSystemGesture = false)
                ) {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 15.dp,
                    ) {
                        Column(
                            modifier = Modifier
                                .width(320.dp)
                                .height(510.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Row( modifier = Modifier.fillMaxWidth()) {
                                IconButton(onClick = { setShowPopup(false) }) {
                                    Icon(Icons.Filled.Close, contentDescription = "favorite")
                                }
                            }
                            CompTitle(text = "Edit post")
                            CompError(error)
                            CompInput(value = popupTitle, setValue = setPopupTitle, label = "Title")
                            OutlinedTextField(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .padding(15.dp)
                                    .requiredWidth(300.dp)
                                    .requiredHeight(200.dp),
                                value = popupContent,
                                onValueChange = setPopupContent
                            )
                            CompButton(onClick = {
                                editPost(
                                    token,
                                    currentId,
                                    popupTitle,
                                    popupContent,
                                    setPosts,
                                    setError
                                );
                                setShowPopup(false) }, label = "Submit changes!")
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    CompTitle("Your notes!");
                }
                posts.forEach { post ->
                    item {
                        CompUserPost(
                            token,
                            post,
                            setPosts,
                            setShowPopup,
                            setPopupTitle,
                            setPopupContent,
                            setCurrentId
                        )
                    }
                }
            }
        }
    }
}

fun getPosts(token: String, setPosts: (List<PostItem>) -> Unit) {
    val body = mapOf("token" to token)

    apiInterface.getHome(body).enqueue( object : Callback<PostResult> {
        override fun onFailure(call: Call<PostResult>, t: Throwable) {
            println("Something went very wrong")
        }
        override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
            val res = response.body() ?: return;

            setPosts(res.posts)
        }
    } )
}