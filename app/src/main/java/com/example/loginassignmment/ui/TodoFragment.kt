package com.example.loginassignmment.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginassignmment.LoginActivity
import com.example.loginassignmment.R
import com.example.loginassignmment.adapter.TodoAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_todos.*
import javax.inject.Inject

class TodoFragment : Fragment(R.layout.fragment_todos) {
    lateinit var todoItemAdapter: TodoAdapter
    lateinit var viewModel : TodoViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoItemAdapter = TodoAdapter()
        viewModel =ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)
        subscribeToObservers()
        setupRecyclerView()

        todoItemAdapter.setOnItemClickListener {
            var bundle = Bundle().apply {
                putSerializable("todo",it)
            }
            findNavController().navigate(R.id.action_todoFragment2_to_updateTodoFragment,bundle)
        }

        fabAddTodoItem.setOnClickListener {
            findNavController().navigate(TodoFragmentDirections.actionTodoFragment2ToAddTodoFragment())
        }
        btnlogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(),LoginActivity::class.java))

        }
    }
    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) =  true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = todoItemAdapter.todoItems[pos]
            viewModel?.deleteTodoItem(item)
            Snackbar.make(requireView(),"Successfully deleted item", Snackbar.LENGTH_LONG).apply {
                setAction("Undo"){
                    viewModel?.insertTodoItemIntoDb(item)
                }
                show()
            }
        }

    }

    private fun subscribeToObservers(){
        viewModel?.todoItems?.observe(viewLifecycleOwner, Observer {
            todoItemAdapter.todoItems = it
        })

    }

    private fun setupRecyclerView(){
        rvTodoItems.apply {
            adapter = todoItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }
}