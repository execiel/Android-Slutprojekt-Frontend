package com.example.myapplication.networking

import com.example.myapplication.networking.objects.LoginResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiInterface {
    @Headers("Content-Type: application/json")

    @POST("registerUser")
    fun registerUser(@Body body: Map<String, String>): Call<Void>

    @POST("loginUser")
    fun loginUser(@Body body: Map<String, String>): Call<LoginResult>
}