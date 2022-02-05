package com.example.loginassignmment.repository

import androidx.lifecycle.LiveData
import com.example.loginassignmment.data.TodoDao
import com.example.loginassignmment.data.TodoItem
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao){
    suspend fun insertTodoItem(todoItem: TodoItem) {
        todoDao.insertTodoItem(todoItem)
    }

    suspend fun deleteTodoItem(todoItem: TodoItem) {
        todoDao.deleteTodoItem(todoItem)
    }

    fun observeAllTodoItems(): LiveData<List<TodoItem>> {
        return todoDao.observeAllTodoItems()
    }

    suspend fun updateTodoItem(todoItem: TodoItem){
        todoDao.updateTodoItem(todoItem)
    }
}