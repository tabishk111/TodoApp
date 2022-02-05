package com.example.loginassignmment.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.loginassignmment.data.TodoDao
import com.example.loginassignmment.data.TodoItemDatabase
import com.example.loginassignmment.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, TodoItemDatabase::class.java, "todo_db").build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: TodoDao
    ) = TodoRepository(dao)


    @Singleton
    @Provides
    fun provideShoppingDao(
        database: TodoItemDatabase
    ) = database.todoDao()
}