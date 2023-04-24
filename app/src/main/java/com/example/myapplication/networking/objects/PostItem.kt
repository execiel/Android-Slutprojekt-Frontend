package com.example.myapplication.networking.objects

import com.google.gson.annotations.SerializedName

data class PostItem(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
)
