package com.example.task_note.data

import androidx.recyclerview.widget.DiffUtil

class TaskDiffCallback(private val oldTasks:List<Task>,private val newTask: List<Task>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldTasks.size

    override fun getNewListSize(): Int = newTask.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        (oldTasks[oldItemPosition] == newTask[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldTasks[oldItemPosition].isContentEqual(newTask[newItemPosition])

}
