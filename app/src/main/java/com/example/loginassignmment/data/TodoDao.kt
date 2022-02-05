package com.example.loginassignmment.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(todoItem: TodoItem)

    @Delete
    suspend fun deleteTodoItem(todoItem: TodoItem)

    @Query("SELECT * FROM todos")
    fun observeAllTodoItems(): LiveData<List<TodoItem>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodoItem(todoItem: TodoItem)
}