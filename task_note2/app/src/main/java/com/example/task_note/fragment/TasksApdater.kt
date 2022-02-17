package com.example.task_note.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task_note.data.Task
import com.example.task_note.data.TaskDiffCallback
import com.example.task_note.databinding.TaskItemBinding

class TasksApdater(
    private val tasks: MutableList<Task> = mutableListOf(),
    private val listener: TaskListener
):RecyclerView.Adapter<TasksApdater.Companion.viewHolder>(), ItemTochHelperListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.title = task.title
        holder.binding.root.setOnClickListener {
            listener.onItemClick(task)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = tasks.size

    override fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int) {
        listener.deleteTaskPosition(task = tasks[position], position = position)
    }

    fun updateTasks(tasks: List<Task>?) =
        tasks?.let {
            val diffCallback = TaskDiffCallback(this.tasks,tasks)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            this.tasks.clear()
            this.tasks.addAll(tasks)
            diffResult.dispatchUpdatesTo(this)
        }

    companion object{
        class viewHolder(val binding:TaskItemBinding):RecyclerView.ViewHolder(binding.root)
    }

    interface TaskListener{
        fun deleteTaskPosition(task: Task, position:Int)
        fun onItemClick(task: Task)
    }

}