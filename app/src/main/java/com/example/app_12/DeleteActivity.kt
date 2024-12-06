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

class DeleteActivity : AppCompatActivity() {

    private lateinit var articles: ArrayList<Article>
    private lateinit var spinner: Spinner
    private lateinit var dbHandler: DatabaseHandler

    private lateinit var deleteButton: Button
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_delete)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHandler = DatabaseHandler(this)
        articles = ArrayList()
        spinner = findViewById(R.id.ID_Spinner_Delete)

        deleteButton = findViewById(R.id.DeleteBtn)
        backBtn = findViewById(R.id.BackBtn_Delete)

        deleteButton.setOnClickListener{
            deleteArticle()
            finish()
        }

        backBtn.setOnClickListener{
            finish()
        }

        fetchArticles()

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

    private fun deleteArticle() {
        val articleId = spinner.selectedItem.toString().toInt()

        dbHandler.deleteArticle(articleId,
            onSuccess = { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            },
            onError = { errorMessage ->
                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        )

    }
}