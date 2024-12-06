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

class ShowActivity : AppCompatActivity() {

    private lateinit var articles: ArrayList<Article>
    private lateinit var spinner: Spinner
    private lateinit var dbHandler: DatabaseHandler

    private lateinit var nameField: EditText
    private lateinit var costField: EditText
    private lateinit var dateField: EditText

    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHandler = DatabaseHandler(this)
        articles = ArrayList()
        spinner = findViewById(R.id.ID_Spinner_List)

        nameField = findViewById(R.id.Name_Field_List)
        costField = findViewById(R.id.Cost_Field_List)
        dateField = findViewById(R.id.Date_Field_List)

        backBtn = findViewById(R.id.BackBtn)

        fetchArticles()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedItem = parentView?.getItemAtPosition(position) as Int
                Toast.makeText(this@ShowActivity, "ID de articulo seleccionado: $selectedItem", Toast.LENGTH_SHORT).show()
                for (article in articles) {
                    if (article.id == selectedItem){
                        nameField.setText(article.name)
                        costField.setText(article.cost.toString())
                        dateField.setText(article.date)
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                nameField.setText("")
                costField.setText("")
                dateField.setText("")
            }
        }

        backBtn.setOnClickListener{
            finish()
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


}
