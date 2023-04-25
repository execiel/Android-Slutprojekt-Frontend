package com.example.myapplication.networking

import com.example.myapplication.apiInterface
import com.example.myapplication.networking.objects.PostResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun deletePost(token: String, id: String) {
    val body = mapOf("token" to token)

    apiInterface.deletePost(body).enqueue( object : Callback<Void> {
        override fun onFailure(call: Call<Void>, t: Throwable) {
            println("Something went very wrong")
        }
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            println(call.cod)
        }
    } )
}