package com.example.task_note.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.task_note.app.hideKeyboard
import com.example.task_note.data.WorkState
import com.example.task_note.databinding.FragmentTasksDetailsBinding
import com.example.task_note.viewmodel.TasksDetailsViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class TaskDetailsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(TasksDetailsViewModel::class.java)
    }

    private var _binding: FragmentTasksDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTasksDetailsBinding.inflate(inflater, container, false)
        binding.also {
            it.lifecycleOwner = this
            it.viewModel = this.viewModel
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {bundle ->
            TaskDetailsFragmentArgs.fromBundle(bundle).task?.let { viewModel.setTask(it) }
        }

        viewModel.workState.observe(viewLifecycleOwner, Observer {
            if(it == WorkState.LOADING){
                view.hideKeyboard()
            }
            if(it == WorkState.FINISH){
                findNavController().navigateUp()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}