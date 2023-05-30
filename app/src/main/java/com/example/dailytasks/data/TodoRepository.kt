package com.example.dailytasks.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(id: Int): Todo?

    fun getAllTodo(): Flow<List<Todo>>
}