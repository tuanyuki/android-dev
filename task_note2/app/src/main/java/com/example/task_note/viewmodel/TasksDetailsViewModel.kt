package com.example.task_note.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.task_note.app.Injection
import com.example.task_note.data.RoomRepository
import com.example.task_note.data.Task
import com.example.task_note.data.WorkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@InternalCoroutinesApi
class TasksDetailsViewModel(
    private val repository: RoomRepository = Injection.roomRepository
):ViewModel() {
    val description = MediatorLiveData<String>()
    val title = MediatorLiveData<String>()
    val canSave: MediatorLiveData<Boolean>
        get() = _canSave
    val workState
        get() = _workState
    val showLoading: LiveData<Boolean>
        get() = Transformations.map(workState) {
            it == WorkState.LOADING
        }

    private val _canSave = MediatorLiveData<Boolean>()
    private val _task = MutableLiveData<Task>()
    val task:LiveData<Task>
        get() = _task
    private val _workState = MutableLiveData<WorkState>()

    init {
        _workState.value = WorkState.INITIAL
        title.addSource(task){
            it.let {
                title.value = it.title
            }
        }
        description.addSource(task){
            it.let {
                description.value = it.description
            }
        }
        _canSave.addSource(title){
            _canSave.value = checkCanSave()
        }
        _canSave.addSource(description){
            _canSave.value = checkCanSave()
        }
    }
    fun setTask(task: Task){
        if (_task.value != task) {
            task.let {
                _task.value = it
            }
        }
    }

    fun saveAction(){
        val task = _task.value
        title.value.let { title ->
            description.value.let { description ->
                if(task != null){
                    task.copy(title = title.toString(), description = description.toString()).run {
                        updateTask(this)
                    }
                }else{
                    Task(title = title.toString(), description = description.toString()).run {
                        saveTask(this)
                    }
                }
            }
        }
    }

    fun checkCanSave(): Boolean{
        val title = title.value
        val descrition = description.value
        return (title?.isEmpty() == false && descrition?.isEmpty() == false && (title != _task.value?.title||descrition != _task.value?.description))
    }

    private fun saveTask(task: Task){
        _workState.value = WorkState.LOADING
        _task.value = task
        viewModelScope.launch(Dispatchers.Default) {
            repository.addTask(task)
            withContext(Dispatchers.Main){
                _workState.value = WorkState.FINISH
            }
        }
    }
    private fun updateTask(task: Task) {
        _workState.value = WorkState.LOADING
        _task.value = task
        viewModelScope.launch(Dispatchers.Default) {
            repository.updateTask(task)
            withContext(Dispatchers.Main) {
                _workState.value = WorkState.FINISH
            }
        }
    }
}