package com.example.app_12

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreateActivity : AppCompatActivity() {

    private lateinit var createBtn: Button
    private lateinit var cancelBtn: Button

    private lateinit var nameField: EditText
    private lateinit var costField: EditText

    private lateinit var dbHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHandler = DatabaseHandler(this)

        createBtn = findViewById(R.id.Create_Btn_Create)
        cancelBtn = findViewById(R.id.Cancel_Btn_Create)

        nameField = findViewById(R.id.Name_Field_Create)
        costField = findViewById(R.id.Cost_Field_Create)

        createBtn.setOnClickListener(){
            createArticle()
        }

        cancelBtn.setOnClickListener(){
            finish()
        }
    }

    private fun createArticle(){
        if (!nameField.text.isNullOrEmpty() && costField.text.toString().toDoubleOrNull() != null){
            val name: String = nameField.text.toString()
            val cost: Double = costField.text.toString().toDouble()
            val newArticle = Article(null, name, cost, null)
            dbHandler.createArticle(
                article = newArticle,
                onSuccess = {
                    Toast.makeText(this, "Article created", Toast.LENGTH_SHORT).show()
                    finish()
                },
                onError = { error ->
                    Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                }
            )
        } else{
            Toast.makeText(this, "Name field or cost field wrong", Toast.LENGTH_SHORT).show()
        }

    }
}