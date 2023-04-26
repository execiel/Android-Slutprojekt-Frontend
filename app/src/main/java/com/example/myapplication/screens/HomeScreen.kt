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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.*
import com.example.myapplication.networking.deletePost
import com.example.myapplication.networking.editPost
import com.example.myapplication.networking.getPosts
import com.example.myapplication.networking.objects.PostItem
import com.example.myapplication.networking.objects.PostResult
import com.example.myapplication.viewmodels.PostsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.IdentityHashMap

@Composable
fun HomeScreen(
    token: String,
    navigateHome: () -> Unit,
    navigateWrite: () -> Unit,
    navigateSettings: () -> Unit,
    ) {

    // View model to handle posts
    val postsViewModel = viewModel<PostsViewModel>();
    val posts by postsViewModel.posts.collectAsState();

    // Popup state
    val (showPopup, setShowPopup) = remember {
        mutableStateOf(false);
    }

    val (currentPost, setCurrentPost) = remember {
        mutableStateOf(PostItem("", "placeholder", "placeholder"))
    }

    getPosts(token, postsViewModel);

    Scaffold(bottomBar = {
        BottomBar("Home", navigateHome, navigateWrite, navigateSettings)
    }) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) {
            if (showPopup) {
                EditPopup(
                    token,
                    setShowPopup,
                    currentPost,
                    setCurrentPost,
                    postsViewModel
                )
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
                            postsViewModel,
                            setShowPopup,
                            setCurrentPost,
                        )
                    }
                }
            }
        }
    }
}
