package errorError.myapplication.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myapplication.*
import com.example.myapplication.networking.deletePost
import com.example.myapplication.networking.deleteUser
import com.example.myapplication.screens.BottomBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    token: String,
    store: UserStore,
    navigateHome: () -> Unit,
    navigateWrite: () -> Unit,
    navigateSearch: () -> Unit,
    navigateSettings: () -> Unit,
    navigateLogin: () -> Unit
) {

    val (password, setPassword) = remember {
        mutableStateOf("")
    }

    val (error, setError) = remember {
        mutableStateOf("")
    }

    Scaffold(bottomBar = {
        BottomBar("Settings", navigateHome, navigateWrite, navigateSearch, navigateSettings)
    }) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CompTitle(text = "Remove account")
                Text("Enter your password to remove account")
                CompInputPassword(value = password, setValue = setPassword, label = "password")
                CompButton(
                    onClick = {
                              deleteUser(token, password, setError, navigateLogin)
                    },
                    label = "Remove account"
                )
                CompError(error)
                CompTitle(text = "Log out from account")
                CompButton(onClick = { logout(store, navigateLogin) }, label = "Log out")
            }
        }
    }
}


// Store token If everything went ok
fun logout(store: UserStore, navigateLogin: () -> Unit) {
    // Remove token
    CoroutineScope(Dispatchers.IO).launch {
        store.saveToken("")
    }

    // Navigate to login screen
    navigateLogin()
}
