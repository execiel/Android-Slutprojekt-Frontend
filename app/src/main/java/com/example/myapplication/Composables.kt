package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.networking.deletePost
import com.example.myapplication.networking.objects.PostItem
import com.example.myapplication.ui.theme.ErrorColor
import com.example.myapplication.ui.theme.Purple500

@Composable
fun CompInput(value: String, setValue: (String) -> Unit, label: String) {
    OutlinedTextField(shape = RoundedCornerShape(10.dp), value = value, onValueChange = setValue, label = { Text(label) })
}

@Composable
 fun CompButton(onClick: () -> Unit, label: String) {
    OutlinedButton(modifier = Modifier.padding(25.dp), shape = RoundedCornerShape(1000.dp), onClick = { onClick() }) {
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
    setPosts: (List<PostItem>) -> Unit
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
                IconButton(onClick = { deletePost(token, post.id, setPosts) }) {
                    Icon(Icons.Filled.Delete, contentDescription = "favorite")
                }
                IconButton(onClick = {println("Hej")}) {
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
        color = Purple500,
        text = "Don't have and account? Register here!"
    )
}

@Composable
fun CompError(text: String) {
    Text(text = text, color = ErrorColor, fontSize = 12.sp)
}