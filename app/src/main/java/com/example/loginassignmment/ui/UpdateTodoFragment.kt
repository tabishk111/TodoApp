package com.example.loginassignmment.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androiddevs.shoppinglisttestingyt.other.Status
import com.example.loginassignmment.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_add_todo_item.*
import kotlinx.android.synthetic.main.fragment_add_todo_item.etTodoItemDescription
import kotlinx.android.synthetic.main.fragment_add_todo_item.etTodoItemName
import kotlinx.android.synthetic.main.fragment_update.*

class UpdateTodoFragment :Fragment(R.layout.fragment_update){
    lateinit var viewModel: TodoViewModel
    val args : UpdateTodoFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)
        subscribeToObservers()
        val todoItem = args.todo
        etTodoItemNameU.setText(todoItem.name)
        etTodoItemDescriptionU.setText(todoItem.description)
        btnUpdateTodoItem.setOnClickListener {
            todoItem.id?.let { it1 ->
                viewModel.updateTodoItem(
                    etTodoItemNameU.text.toString(),
                    etTodoItemDescriptionU.text.toString(),
                    it1
                )
            }
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeToObservers(){

        viewModel.updateTodoItemStatus.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS->{
                        Snackbar.make(requireActivity().rootLayout,"Updated Todo Item", Snackbar.LENGTH_LONG).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR->{
                        Snackbar.make(requireActivity().rootLayout, result.message?: "An unknown error occurred", Snackbar.LENGTH_LONG).show()
                    }
                    Status.LOADING->{
                        /*NO-OP*/
                    }
                }
            }
        })
    }
}