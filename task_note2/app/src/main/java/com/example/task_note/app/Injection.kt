package com.example.task_note.app

import com.example.task_note.data.RoomRepository
import kotlinx.coroutines.InternalCoroutinesApi
@InternalCoroutinesApi
object Injection {
    val roomRepository = RoomRepository()
}