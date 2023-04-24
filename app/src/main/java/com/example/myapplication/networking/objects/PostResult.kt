package com.example.myapplication.networking.objects

import com.google.gson.annotations.SerializedName

data class PostResult(
    @SerializedName("posts") val posts: List<PostItem>,
)
