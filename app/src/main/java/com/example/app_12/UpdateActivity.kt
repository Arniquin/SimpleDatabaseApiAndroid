package com.example.app_12

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UpdateActivity : AppCompatActivity() {

    private lateinit var articles: ArrayList<Article>
    private lateinit var spinner: Spinner
    private lateinit var dbHandler: DatabaseHandler

    private lateinit var nameField: EditText
    private lateinit var costField: EditText

    private lateinit var updateBtn: Button
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHandler = DatabaseHandler(this)
        articles = ArrayList()
        spinner = findViewById(R.id.ID_Spinner_Update)

        nameField = findViewById(R.id.Name_Field_Update)
        costField = findViewById(R.id.Cost_Field_Update)

        updateBtn = findViewById(R.id.UpdateBtn)
        backBtn = findViewById(R.id.BackBtn_Update)

        updateBtn.setOnClickListener{
            updateArticle()
            finish()
        }

        backBtn.setOnClickListener{
            finish()
        }

        fetchArticles()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedItem = parentView?.getItemAtPosition(position) as Int
                Toast.makeText(this@UpdateActivity, "ID de articulo seleccionado: $selectedItem", Toast.LENGTH_SHORT).show()
                for (article in articles) {
                    if (article.id == selectedItem){
                        nameField.setText(article.name)
                        costField.setText(article.cost.toString())
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                nameField.setText("")
                costField.setText("")
            }
        }
    }

    private fun fetchArticles() {
        dbHandler.fetchAllArticles(
            onSuccess = { fetchedArticles ->
                articles.clear()
                articles.addAll(fetchedArticles)

                val articlesId: ArrayList<Int> = ArrayList()
                for (article in articles) {
                    article.id?.let { articlesId.add(it) }
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, articlesId)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            },
            onError = { error ->
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                println("Error: $error")
                finish()
            }
        )
    }

    private fun updateArticle() {
        if (!nameField.text.isNullOrEmpty() && costField.text.toString().toDoubleOrNull() != null){
            val id: Int = spinner.selectedItem.toString().toInt()
            val name: String = nameField.text.toString()
            val cost: Double = costField.text.toString().toDouble()

            val newArticle = Article(id, name, cost, null)
            dbHandler.updateArticle(newArticle,
                onSuccess = { message ->
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    println(message)
                },
                onError = { errorMessage ->
                    Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                    println("Error: $errorMessage")
                }
            )
        } else{
            Toast.makeText(this, "Name field or cost field wrong", Toast.LENGTH_SHORT).show()
        }
    }
}