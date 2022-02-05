package com.example.loginassignmment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.loginassignmment.R
import com.example.loginassignmment.data.TodoItem
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoItemViewHolder>() {

    class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var todoItems: List<TodoItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        return TodoItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.item_todo,
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = todoItems[position]
        holder.itemView.apply {
            tvName.text = todoItem.name
            tvTodoDescription.text = todoItem.description
            setOnClickListener{
                onItemClickListener?.let { it(todoItem) }
            }
        }
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }

    private var onItemClickListener: ((TodoItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (TodoItem) -> Unit){
        onItemClickListener = listener
    }
}