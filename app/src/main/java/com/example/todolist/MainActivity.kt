package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var todoItemRecyclerView: RecyclerView
    private lateinit var recyclerAdapter: ToDoItemsAdapter
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager

    var todoItemsList = ArrayList<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllItems(dbo)
        with(cursor){
            while(moveToNext()){
                val itemName = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemUrgency = getInt(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY))
                val isUrgent = if(itemUrgency == 0) false else true
                todoItemsList.add(TodoItem(itemName, isUrgent))
            }
        }

        /*todoItemsList.add(TodoItem("Buy groceries"))
        todoItemsList.add(TodoItem("Do laundry", true))
        todoItemsList.add(TodoItem("Play guitar", false))*/

        todoItemRecyclerView = findViewById(R.id.todo_item_recycle_view)

        recyclerLayoutManager = LinearLayoutManager(this)
        recyclerAdapter = ToDoItemsAdapter(todoItemsList, this)

        todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }
    }
    fun navToAddItemAction(view: View){
        val intent: Intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }
}