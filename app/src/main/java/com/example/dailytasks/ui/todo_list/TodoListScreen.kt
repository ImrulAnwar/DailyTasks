package com.example.dailytasks.ui.todo_list

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dailytasks.ui.todo_list.TodoListEvent
import com.example.dailytasks.ui.todo_list.TodoListViewModel
import com.example.dailytasks.util.UiEvent
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.dailytasks.ui.theme.myThemeColor


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    changeStatusBarColor()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Column(Modifier.fillMaxSize()){
        TopAppBar(
            title = {
                Text(
                    text = "All Tasks    ",
                    modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally),
                    fontSize = 40.sp
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* Handle navigation icon click */ }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(TodoListEvent.OnAddTodoClick)
                    },
                    contentColor = Color.White,
                    containerColor = myThemeColor
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                    )
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(todos.value) { todo ->
                    TodoItem(
                        todo = todo,
                        onEvent = viewModel::onEvent,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.onEvent(TodoListEvent.OnTodoClick(todo))
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun changeStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    val isDarkTheme = isSystemInDarkTheme()

    val statusBarColor = if (isDarkTheme) {
        Color.Black // Set dark color for dark theme
    } else {
        Color.White // Set light color for light theme
    }

    systemUiController.setStatusBarColor(
        color = statusBarColor,
        darkIcons = !isDarkTheme // Invert darkIcons based on the theme
    )
}