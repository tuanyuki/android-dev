package com.example.task_note.viewmodel

import androidx.lifecycle.*
import androidx.room.Room
import com.example.task_note.app.Injection
import com.example.task_note.data.RoomRepository
import com.example.task_note.data.Task
import com.example.task_note.data.TaskRepository
import com.example.task_note.data.WorkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@InternalCoroutinesApi
class TasksViewModel(private val repository:TaskRepository = Injection.roomRepository
):ViewModel() {
    val task:LiveData<List<Task>?> = repository.getTasks()

    private val _workState = MutableLiveData<WorkState>()
    val workState:LiveData<WorkState>
        get() = _workState

    private val _isEmpty = MediatorLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        _isEmpty.addSource(task){
            _isEmpty.postValue(it?.isNullOrEmpty() ?: true)
        }
    }

    fun deleteTask(task: Task){
        _workState.value = WorkState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
            _workState.postValue(WorkState.FINISH)
        }
    }
}