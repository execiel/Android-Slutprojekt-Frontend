package com.example.myapplication.networking

import com.example.myapplication.networking.objects.LoginResult
import com.example.myapplication.networking.objects.PostResult
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @Headers("Content-Type: application/json")

    @POST("registerUser")
    fun registerUser(@Body body: Map<String, String>): Call<Void>

    @POST("loginUser")
    fun loginUser(@Body body: Map<String, String>): Call<LoginResult>

    @POST("addPost")
    fun addPost(@Body body: Map<String, String>): Call<Void>

    @POST("getHome")
    fun getHome(@Body body: Map<String, String>): Call<PostResult>

    @HTTP(method = "DELETE", path = "deletePost", hasBody = true)
    fun deletePost(@Body body: Map<String, String>): Call<PostResult>

    @HTTP(method = "DELETE", path = "deleteUser", hasBody = true)
    fun deleteUser(@Body body: Map<String, String>): Call<Void>

    @HTTP(method = "PUT", path = "editPost", hasBody = true)
    fun editPost(@Body body: Map<String, String>): Call<PostResult>
}