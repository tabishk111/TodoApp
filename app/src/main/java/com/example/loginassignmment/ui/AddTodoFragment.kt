package com.example.loginassignmment.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androiddevs.shoppinglisttestingyt.other.Status
import com.example.loginassignmment.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_add_todo_item.*

class AddTodoFragment : Fragment(R.layout.fragment_add_todo_item) {

    lateinit var viewModel: TodoViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)
        subscribeToObservers()
        btnAddTodoItem.setOnClickListener {
            viewModel.insertTodoItem(
                etTodoItemName.text.toString(),
                etTodoItemDescription.text.toString()
            )
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeToObservers(){

        viewModel.insertTodoItemStatus.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { result ->
                when(result.status) {
                    Status.SUCCESS->{
                        Snackbar.make(requireActivity().rootLayout,"Added Todo Item", Snackbar.LENGTH_LONG).show()
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