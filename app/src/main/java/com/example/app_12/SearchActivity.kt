package com.example.app_12

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SearchActivity : AppCompatActivity() {
//    @SuppressLint("MissingInflatedId")

    private lateinit var articles: ArrayList<Article>
    private lateinit var dbHandler: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHandler = DatabaseHandler(this)
        articles = ArrayList()

        val rbID: RadioButton = findViewById(R.id.radioButtonId)
        val rbName: RadioButton = findViewById(R.id.radioButtonNombre)

        val searchText: EditText = findViewById(R.id.SearchText)
        val searchBtn: Button = findViewById(R.id.SearchBtn)

        val idField: EditText = findViewById(R.id.ID_FieldSearch)
        val nameField: EditText = findViewById(R.id.Name_FieldSearch)
        val costField: EditText = findViewById(R.id.Cost_FieldSearch)
        val dateField: EditText = findViewById(R.id.Date_FieldSearch)

        val backBtn: Button = findViewById(R.id.BackBtnSearch)

        fetchArticles()

        rbID.isChecked = true

        rbID.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rbName.isChecked = false
            }
        }

        rbName.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rbID.isChecked = false
            }
        }

        searchBtn.setOnClickListener{
            if(!searchText.text.isNullOrEmpty()){
                var searchCriteria =searchText.text.toString()
                var found: Boolean = false
                if(rbID.isChecked){
                    for(article in articles){
                        if(searchCriteria == article.id.toString()){
                            idField.setText(article.id.toString())
                            nameField.setText(article.name)
                            costField.setText(article.cost.toString())
                            dateField.setText(article.date)
                            found = true
                        }
                    }
                }
                if(rbName.isChecked){
                    for(article in articles){
                        if(searchCriteria == article.name){
                            idField.setText(article.id.toString())
                            nameField.setText(article.name)
                            costField.setText(article.cost.toString())
                            dateField.setText(article.date)
                            found = true
                        }
                    }
                }

                if(!found){
                    Toast.makeText(this, "Article not found", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(this, "Search text is empty", Toast.LENGTH_SHORT).show()
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
            },
            onError = { error ->
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                println("Error: $error")
                finish()
            }
        )
    }
}