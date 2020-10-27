package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var todoItemRecyclerView: RecyclerView
    private lateinit var recyclerAdapter: ToDoItemsAdapter
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager

    var todoItemsList = ArrayList<TodoItem>()
    var todaysItemsList = ArrayList<TodoItem>()
    var pastItemsList = ArrayList<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllItems(dbo)
        with(cursor){
            while(moveToNext()){
                val itemName = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemUrgency = getInt(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY))
                val itemDate = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_DATE))
                val isUrgent = if(itemUrgency == 0) false else true

                val newTodoItems = TodoItem(itemName, isUrgent)
                newTodoItems.dateString = itemDate
                todoItemsList.add(newTodoItems)

                if (itemDate == getDateAsString()){
                    todaysItemsList.add(newTodoItems)
                }else{
                    pastItemsList.add(newTodoItems)
                }
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

    public fun displayTodaysItems(view: View){
        recyclerAdapter = ToDoItemsAdapter(todaysItemsList, this)

        todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }
    }

    public fun displayPastItems(view: View){
        recyclerAdapter = ToDoItemsAdapter(pastItemsList, this)

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
    fun getDateAsString(): String{
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR).toString()
        val month = date.get(Calendar.MONTH).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()
        return "$year/$month/$day"
    }
}