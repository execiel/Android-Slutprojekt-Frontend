package com.example.myapplication.networking

import com.example.myapplication.UserStore
import com.example.myapplication.apiInterface
import com.example.myapplication.networking.objects.PostItem
import com.example.myapplication.networking.objects.PostResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

            setPosts(res.posts)
        }
    } )
}

fun deleteUser(store: UserStore, token: String, password: String, setError: (String) -> Unit, navigateLogin: () -> Unit) {
    val body = mapOf("token" to token, "password" to password)

    if(password == "") {
        setError("Password field is empty")
        return;
    }

    apiInterface.deleteUser(body).enqueue( object : Callback<Void> {
        override fun onFailure(call: Call<Void>, t: Throwable) {
            println("Something went very wrong")
        }
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            println(response.code())
            if(response.code() == 200) {
                navigateLogin()
                // Remove token
                CoroutineScope(Dispatchers.IO).launch {
                    store.saveToken("")
                }
                return
            }

            setError("Wrong password")
        }
    } )
}

fun editPost(
    token: String,
    id: String,
    title: String,
    content: String,
    setPosts: (List<PostItem>) -> Unit,
    setError: (String) -> Unit,
) {
    val body = mapOf(
        "token" to token,
        "id" to id,
        "title" to title,
        "content" to content
    )

    if(id == "" || title == "" || content == "") {
        setError("You must fill out all fields")
        return
    }

    if(title.length > 200 || content.length > 1000) {
        setError("Title or content to long")
        return
    }

    apiInterface.editPost(body).enqueue( object : Callback<PostResult> {
        override fun onFailure(call: Call<PostResult>, t: Throwable) {
            println("Something went very wrong")
        }
        override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
            val res = response.body() ?: return;

            setPosts(res.posts)
        }
    } )
}
