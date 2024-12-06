package com.example.app_12

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShowRecyclerActivity : AppCompatActivity() {

    private lateinit var backBtn: Button
    private lateinit var articles: ArrayList<Article>
    private lateinit var dbHandler: DatabaseHandler
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_recycler)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backBtn = findViewById(R.id.BackBtnRecylcer)
        dbHandler = DatabaseHandler(this)
        articles = ArrayList()
        recyclerView = findViewById(R.id.recyclerView)

        fetchArticles()

        backBtn.setOnClickListener{
            finish()
        }


    }

    private fun fetchArticles() {
        dbHandler.fetchAllArticles(
            onSuccess = { fetchedArticles ->
                articles.clear()
                articles.addAll(fetchedArticles)
                val adapter = ArticleAdapter(articles)

                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = adapter


            },
            onError = { error ->
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                println("Error: $error")
                finish()
            }
        )
    }
}