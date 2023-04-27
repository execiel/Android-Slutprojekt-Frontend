import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.UserStore
import com.example.myapplication.networking.ApiInterface
import com.example.myapplication.screens.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import errorError.myapplication.screens.SettingsScreen
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


val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:3000/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiInterface = retrofit.create(ApiInterface::class.java)

// Navigation host
@Composable
fun MainNavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val store = UserStore(LocalContext.current)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    val (startDestination, setStartDestination) = remember {mutableStateOf("login")}

    if(tokenText.value != "")
        setStartDestination("home")

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                { navigateTo(navController, "login", setStartDestination)},
                store,
                { navigateTo(navController, "home", setStartDestination)},
            )
        }
        composable("register") {
            RegisterScreen(
                { navigateTo(navController, "login", setStartDestination)},
            )
        }
        composable("home") {
            HomeScreen(
                tokenText.value,
                { navigateTo(navController, "home", setStartDestination)},
                { navigateTo(navController, "write", setStartDestination)},
                { navigateTo(navController, "settings", setStartDestination)},
            )
        }
        composable("write") {
            WriteScreen(
                tokenText.value,
                { navigateTo(navController, "home", setStartDestination)},
                { navigateTo(navController, "write", setStartDestination)},
                { navigateTo(navController, "settings", setStartDestination)},
            )
        }
        composable("settings") {
            SettingsScreen(
                tokenText.value,
                store,
                { navigateTo(navController, "home", setStartDestination)},
                { navigateTo(navController, "write", setStartDestination)},
                { navigateTo(navController, "settings", setStartDestination)},
                { navigateTo(navController, "login", setStartDestination)},
            )
        }
    }
}

fun navigateTo(navCtl: NavHostController, route: String, setStart: (String) -> Unit) {
    setStart(route);
    navCtl.navigate(route);
}