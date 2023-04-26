package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication.networking.objects.PostItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostsViewModel: ViewModel() {
    private val _posts =
        MutableStateFlow(
            listOf(
                PostItem(title = "You haven't taken any notes", content = "", id = "w")
            )
        );
    val posts = _posts.asStateFlow()

    fun setPosts(posts: List<PostItem>) {
        _posts.value = posts;
    }
}