package com.example.loginassignmment.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.shoppinglisttestingyt.other.Constants
import com.androiddevs.shoppinglisttestingyt.other.Event
import com.androiddevs.shoppinglisttestingyt.other.Resource
import com.example.loginassignmment.data.TodoItem
import com.example.loginassignmment.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel @ViewModelInject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    val todoItems = repository.observeAllTodoItems()

    private val _insertTodoItemStatus = MutableLiveData<Event<Resource<TodoItem>>>()
    val insertTodoItemStatus: LiveData<Event<Resource<TodoItem>>> = _insertTodoItemStatus

    private val _updateTodoItemStatus = MutableLiveData<Event<Resource<TodoItem>>>()
    val updateTodoItemStatus: LiveData<Event<Resource<TodoItem>>> = _updateTodoItemStatus

    fun deleteTodoItem(todoItem: TodoItem) = viewModelScope.launch {
        repository.deleteTodoItem(todoItem)
    }

    fun insertTodoItemIntoDb(todoItem: TodoItem) = viewModelScope.launch {
        repository.insertTodoItem(todoItem)
    }

    fun insertTodoItem(name: String, description: String) {
        if(name.isEmpty() || description.isEmpty()){
            _insertTodoItemStatus.postValue(Event(Resource.error("Fields cannot be empty",null)))
            return
        }

        if(name.length> Constants.MAX_NAME_LENGTH){
            _insertTodoItemStatus.postValue(Event(Resource.error("Name of item must not exceed ${Constants.MAX_NAME_LENGTH} characters",null)))
            return
        }

        val todoItem = TodoItem(name,description,)
        insertTodoItemIntoDb(todoItem)
        _insertTodoItemStatus.postValue(Event(Resource.success(todoItem)))

    }

    fun updateTodoItemIntoDb(todoItem: TodoItem) = viewModelScope.launch {
        repository.updateTodoItem(todoItem)
    }

    fun updateTodoItem(name: String, description: String,id:Int) {
        if(name.isEmpty() || description.isEmpty()){
            _insertTodoItemStatus.postValue(Event(Resource.error("Fields cannot be empty",null)))
            return
        }

        if(name.length> Constants.MAX_NAME_LENGTH){
            _insertTodoItemStatus.postValue(Event(Resource.error("Name of item must not exceed ${Constants.MAX_NAME_LENGTH} characters",null)))
            return
        }

        val todoItem = TodoItem(name,description,id)
        updateTodoItemIntoDb(todoItem)
        _updateTodoItemStatus.postValue(Event(Resource.success(todoItem)))

    }
}