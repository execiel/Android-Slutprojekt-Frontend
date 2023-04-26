package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.myapplication.networking.deletePost
import com.example.myapplication.networking.editPost
import com.example.myapplication.networking.objects.PostItem
import com.example.myapplication.ui.theme.Aqua500
import com.example.myapplication.ui.theme.ErrorColor
import com.example.myapplication.ui.theme.Peach200
import com.example.myapplication.viewmodels.PostsViewModel

@Composable
fun CompInput(value: String, setValue: (String) -> Unit, label: String) {
    OutlinedTextField(
        modifier = Modifier.padding(10.dp).requiredWidth(300.dp).requiredHeight(65.dp),
        shape = RoundedCornerShape(10.dp),
        value = value,
        onValueChange = setValue,
        label = { Text(label) }
    )
}

@Composable
fun CompInputPassword(value: String, setValue: (String) -> Unit, label: String) {
    OutlinedTextField(
        modifier = Modifier.padding(10.dp).requiredWidth(300.dp).requiredHeight(65.dp),
        shape = RoundedCornerShape(10.dp),
        value = value,
        onValueChange = setValue,
        label = { Text(label) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
 fun CompButton(onClick: () -> Unit, label: String) {
    OutlinedButton(
        modifier = Modifier.padding(15.dp),
        shape = RoundedCornerShape(1000.dp),
        onClick = { onClick() }
    ) {
        Text(text = label, fontSize = 15.sp)
    }
}

@Composable
fun CompTitle(text: String) {
    Text(modifier = Modifier.padding(15.dp), text = text, fontSize = 22.sp)
}

@Composable
fun CompUserPost(
    token: String,
    post: PostItem,
    viewModel: PostsViewModel,
    setShowPopup: (Boolean) -> Unit,
    setCurrentPost: (PostItem) -> Unit,
) {
    val paddingModifier = Modifier.padding(10.dp)
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(5.dp)
            .width(300.dp)
    ) {
        Column() {
            Text(text = post.title, modifier = paddingModifier, fontSize = 18.sp)
            Text(text = post.content, modifier = paddingModifier)
            Row() {
                IconButton(onClick = { deletePost(token, post.id, viewModel) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "favorite")
                }
                IconButton(onClick = {
                    setShowPopup(true);
                    setCurrentPost(post)
                }) {
                    Icon(Icons.Filled.Edit, contentDescription = "favorite")
                }
            }
        }
    }
}

@Composable
fun CompPost(title: String, content: String) {
    val paddingModifier = Modifier.padding(10.dp)
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        modifier = Modifier
            .padding(5.dp)
            .width(300.dp)
    ) {
        Column() {
            Text(text = title, modifier = paddingModifier, fontSize = 18.sp)
            Text(text = content, modifier = paddingModifier)
        }
    }
}

@Composable
fun CompLink(onClick: () -> Unit) {
    Text(
        modifier = Modifier
            .clickable(true) { onClick() },
        color = Aqua500,
        text = "Don't have and account? Register here!"
    )
}

@Composable
fun CompError(text: String) {
    Text(text = text, color = ErrorColor, fontSize = 12.sp)
}

@Composable
fun EditPopup(
    token: String,
    setShowPopup: (Boolean) -> Unit,
    currentPost: PostItem,
    setCurrentPost: (PostItem) -> Unit,
    postsViewModel: PostsViewModel,
) {

    // Stores current id for edit popup
    val (error, setError) = remember {
        mutableStateOf("");
    }

    Popup (
        alignment = Alignment.Center,
        onDismissRequest = { setShowPopup(false) },
        properties = PopupProperties(focusable = true, excludeFromSystemGesture = false)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = 15.dp,
        ) {
            Column(
                modifier = Modifier
                    .width(320.dp)
                    .height(510.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row( modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = { setShowPopup(false) }) {
                        Icon(Icons.Filled.Close, contentDescription = "favorite")
                    }
                }
                CompTitle(text = "Edit post")
                CompError(error)
                OutlinedTextField(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.padding(10.dp).requiredWidth(300.dp).requiredHeight(55.dp),
                    value = currentPost.title,
                    onValueChange = {
                        setCurrentPost(
                            PostItem(
                                id = currentPost.id,
                                title = it,
                                content = currentPost.content
                            )
                        )
                    }
                )
                OutlinedTextField(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(15.dp)
                        .requiredWidth(300.dp)
                        .requiredHeight(200.dp),
                    value = currentPost.content,
                    onValueChange = {
                        setCurrentPost(
                            PostItem(
                                id = currentPost.id,
                                title = currentPost.title,
                                content = it
                            )
                        )
                    }
                )
                CompButton(onClick = {
                    editPost(
                        token,
                        currentPost,
                        postsViewModel,
                        setError
                    );
                    setShowPopup(false) }, label = "Submit changes!")
            }
        }
    }
}
