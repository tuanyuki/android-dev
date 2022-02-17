package com.example.task_note.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.task_note.app.navigate
import com.example.task_note.data.Task
import com.example.task_note.databinding.FragmentTasksBinding
import com.example.task_note.viewmodel.TasksViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class TasksFragment : Fragment(), TasksApdater.TaskListener {

    private var _binding: FragmentTasksBinding? = null

    private val viewModel by lazy {
        ViewModelProvider(this).get(TasksViewModel::class.java)
    }
    private val adapter by lazy {
        TasksApdater(listener = this)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setupList()
        viewModel.task.observe(viewLifecycleOwner, Observer {
            adapter.updateTasks(it)
        })
        binding.add.setOnClickListener {
            viewDetail()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun deleteTaskPosition(task: Task, position: Int) {
        viewModel.deleteTask(task)
    }

    override fun onItemClick(task: Task) {
        viewDetail(task)
    }
    private fun viewDetail(task: Task? = null){
        navigate(TasksFragmentDirections.viewDetails(task))
    }

    private fun setupList(){
        binding.listRecyclerView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.listRecyclerView)
    }
}