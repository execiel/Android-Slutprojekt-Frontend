package com.example.myapplication.networking

import com.example.myapplication.UserStore
import com.example.myapplication.apiInterface
import com.example.myapplication.networking.objects.LoginResult
import com.example.myapplication.networking.objects.PostItem
import com.example.myapplication.networking.objects.PostResult
import com.example.myapplication.viewmodels.PostsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun deletePost(token: String, id: String, viewModel: PostsViewModel) {
    val body = mapOf("token" to token, "id" to id)

    apiInterface.deletePost(body).enqueue( object : Callback<PostResult> {
        override fun onFailure(call: Call<PostResult>, t: Throwable) {
            println("Something went very wrong")
        }
        override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
            val res = response.body() ?: return;

            viewModel.setPosts(res.posts)
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
    post: PostItem,
    viewModel: PostsViewModel,
    setError: (String) -> Unit,
) {
    val body = mapOf(
        "token" to token,
        "id" to post.id,
        "title" to post.title,
        "content" to post.content
    )

    if(post.id == "" || post.title == "" || post.content == "") {
        setError("You must fill out all fields")
        return
    }

    if(post.title.length > 200 || post.content.length > 1000) {
        setError("Title or content to long")
        return
    }

    apiInterface.editPost(body).enqueue( object : Callback<PostResult> {
        override fun onFailure(call: Call<PostResult>, t: Throwable) {
            println("Something went very wrong")
        }
        override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
            val res = response.body() ?: return;

            viewModel.setPosts(res.posts)
        }
    } )
}


// Gets post belonging to user from token
fun getPosts(token: String, viewModel: PostsViewModel) {
    val body = mapOf("token" to token)

    apiInterface.getHome(body).enqueue( object : Callback<PostResult> {
        override fun onFailure(call: Call<PostResult>, t: Throwable) {
            println("Something went very wrong")
        }
        override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
            val res = response.body() ?: return;

            viewModel.setPosts(res.posts)
        }
    } )
}

// Verifies and tries to add user to database
fun attemptRegister(
    password: String,
    verifyPassword: String,
    username: String,
    navigateLoginScreen: () -> Unit,
    setError: (String) -> Unit
) {
    // Check if any field is empty
    if(password == "" || verifyPassword == "" || username == "")
        return setError("You need to fill out all fields")

    // Check that passwords match
    if(password != verifyPassword)
        return setError("Passwords don't match")

    // Send information to backend
    val body = mapOf("username" to username, "password" to password)

    apiInterface.registerUser(body).enqueue( object : Callback<Void> {
        override fun onFailure(call: Call<Void>, t: Throwable) {
            setError("Connection error")
        }
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.code() == 200) {
                navigateLoginScreen()
                return
            }
            setError("User already exists");
            return
        }
    } )
}

fun attemptLogin(
    username: String,
    password: String,
    setError: (String) -> Unit,
    store: UserStore,
    navigateHomeScreen: () -> Unit
) {
    // Check if any field is empty
    if(password == "" || username == "")
        setError("You need to fill out all fields")

    // Send information to backend
    val body = mapOf("username" to username, "password" to password)

    // Make call to api
    apiInterface.loginUser(body).enqueue(object : Callback<LoginResult> {
        override fun onFailure(call: Call<LoginResult>, t: Throwable) {
            setError("Can't connect to server")
        }
        override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
            // Make sure response body isn't null
            val res = response.body() ?: return;

            // If connection worked but status is wrong
            if (res.status != "ok") {
                setError("Incorrect username or password")
                return;
            }

            // Store token If everything went ok
            CoroutineScope(Dispatchers.IO).launch {
                store.saveToken(res.token.toString())
            }

            // Navigate to home screen
            navigateHomeScreen()
        }
    } )

}
