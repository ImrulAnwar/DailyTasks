package com.example.dailytasks.di

import android.app.Application
import androidx.room.Room
import com.example.dailytasks.data.Todo
import com.example.dailytasks.data.TodoDatabase
import com.example.dailytasks.data.TodoRepository
import com.example.dailytasks.data.TodoRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app:Application):TodoDatabase{
        return Room.databaseBuilder(
            app, TodoDatabase:: class.java,TodoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(database: TodoDatabase): TodoRepository {
        return TodoRepositoryImplementation(database.todoDao)
    }
}