package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.CompTitle
import com.example.myapplication.CompUserPost
import com.example.myapplication.apiInterface
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

    val (posts, setPosts) = remember {
        mutableStateOf(listOf(PostItem(title = "You haven't taken any notes", content = "", id = "w")));
    }

    getPosts(token, setPosts);

    Scaffold(bottomBar = {
        BottomBar("Home", navigateHome, navigateWrite, navigateSearch, navigateSettings)
    }) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) {
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
                        CompUserPost(post.title, post.title, setTest("hej"), setTest("då"))
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