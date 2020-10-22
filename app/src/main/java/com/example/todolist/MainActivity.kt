package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var todoItemRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoItemRecyclerView = findViewById(R.id.todo_item_recycle_view)
    }
    fun navToAddItemAction(view: View){
        val intent: Intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }
}