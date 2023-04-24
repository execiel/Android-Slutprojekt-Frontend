package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    Text(modifier = Modifier.padding(15.dp), text = text, fontSize = 18.sp)
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