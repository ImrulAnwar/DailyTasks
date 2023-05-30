package com.example.dailytasks.ui.todo_list

import androidx.lifecycle.ViewModel
import com.example.dailytasks.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val repository: TodoRepository): ViewModel(){

}