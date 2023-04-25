package com.example.myapplication.networking

import com.example.myapplication.apiInterface
import com.example.myapplication.networking.objects.PostItem
import com.example.myapplication.networking.objects.PostResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun deletePost(token: String, id: String, setPosts: (List<PostItem>) -> Unit) {
    val body = mapOf("token" to token, "id" to id)

    apiInterface.deletePost(body).enqueue( object : Callback<PostResult> {
        override fun onFailure(call: Call<PostResult>, t: Throwable) {
            println("Something went very wrong")
        }
        override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
            val res = response.body() ?: return;

            for(post in res.posts) {
                println(post.content)
            }

            setPosts(res.posts)
        }
    } )
}