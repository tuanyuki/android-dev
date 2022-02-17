package com.example.task_note.app

import android.app.Application
import com.example.task_note.data.TaskDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class TaskApplication: Application() {
    companion object{
        lateinit var database:TaskDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = TaskDatabase.getDatabase(this)

    }
}