package com.example.myapplication.networking.objects

import com.google.gson.annotations.SerializedName

data class LoginResult (
    @SerializedName("status") val status: String?,
    @SerializedName("token") val token: String?,
)
