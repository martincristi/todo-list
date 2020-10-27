package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AddItemActivity : AppCompatActivity() {

    private lateinit var itemNameEditTextView: EditText
    private lateinit var isUrgentCheckBox: CheckBox
    private lateinit var titleTextView: TextView

    private var isNewItem = true
    private lateinit var oldItem: TodoItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        itemNameEditTextView = findViewById(R.id.item_name_edit_text)
        isUrgentCheckBox = findViewById(R.id.is_urgent_checkbox)
        titleTextView = findViewById(R.id.add_item_title_text_view)

        val itemName = intent.getStringExtra("ITEM_NAME")
        val itemUrgency = intent.getBooleanExtra("ITEM_URGENCY", false)

        if(itemName != null){
            itemNameEditTextView.setText(itemName)
            titleTextView.setText(R.string.edit_item_message)

            oldItem = TodoItem(itemName)
            isNewItem = true

        }
        if(itemUrgency == true){
            isUrgentCheckBox.isChecked = true
        }

    }
    public fun saveItemAction (view: View) {
        val itemName = itemNameEditTextView.text.toString()
        val isItemUrgent = isUrgentCheckBox.isChecked
        val newTodoItem = TodoItem(itemName, isItemUrgent)

        val dbo = DatabaseOperations(this)
        if (isNewItem) {
            dbo.addItem(dbo, newTodoItem)
        } else {
            dbo.updateItem(dbo, this.oldItem, newTodoItem)
        }
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    public fun cancelAction(view: View){
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}