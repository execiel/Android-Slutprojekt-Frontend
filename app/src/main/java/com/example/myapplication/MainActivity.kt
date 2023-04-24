package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.networking.ApiInterface
import com.example.myapplication.screens.LoginScreen
import com.example.myapplication.screens.RegisterScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainNavigationHost()
                }
            }
        }
    }
}


// val client = OkHttpClient.Builder().build()

val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:3000/api/")
    .addConverterFactory(GsonConverterFactory.create())
    // .client(client)//
    .build()

val apiInterface = retrofit.create(ApiInterface::class.java)

// Navigation host
@Composable
fun MainNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "login"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                {navController.navigate("login")}
            )
        }
    }

}