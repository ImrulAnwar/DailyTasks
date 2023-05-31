package com.example.dailytasks.ui.todo_list

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dailytasks.data.Todo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var isDone by remember { mutableStateOf(todo.isDone) }
    val textDecoration = if (isDone) TextDecoration.LineThrough else null
    val textColor = if (isDone) Color.Gray else Color.Black
    Row(
        modifier = modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(textDecoration = textDecoration)) {
                            append(todo.title)
                        }
                    },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
            todo.description?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = textColor)
            }
        }
        Checkbox(
            checked = todo.isDone,
            onCheckedChange = { isChecked ->
                onEvent(TodoListEvent.OnDoneChange(todo, isChecked))
                isDone = isChecked
            }
        )
    }
}