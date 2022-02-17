package com.example.task_note.data

import androidx.lifecycle.LiveData
import com.example.task_note.app.TaskApplication
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class RoomRepository : TaskRepository {
    private val taskDao:TaskDao = TaskApplication.database.taskDao()
    private val allTasks: LiveData<List<Task>?>
    init {
        allTasks = taskDao.getAllTasks()
    }
    override suspend fun addTask(task: Task) {
        taskDao.insert(task)
    }

    override fun getTasks(): LiveData<List<Task>?> = taskDao.getAllTasks()

    override suspend fun updateTask(task: Task) {
        taskDao.update(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }
}