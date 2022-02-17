package com.example.task_note.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(entities = [(Task::class)], version = 1)
abstract class TaskDatabase : RoomDatabase(){
    abstract fun taskDao(): TaskDao

    companion object{
        private const val databaseName = "task_database"

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context):TaskDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            return synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    databaseName
                ).build().apply {
                    INSTANCE = this
                }
            }
        }
    }
}