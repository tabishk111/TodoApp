package com.example.loginassignmment.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todos")
data class TodoItem(
    var name: String,
    var description: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
):Serializable