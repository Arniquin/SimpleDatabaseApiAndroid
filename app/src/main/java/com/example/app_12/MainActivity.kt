package com.example.app_12

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var create: Button
    private lateinit var eliminate: Button
    private lateinit var update: Button
    private lateinit var show: Button
    private lateinit var showRecycler: Button
    private lateinit var searchRecycler: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        create = findViewById(R.id.CrearBtn)
        eliminate = findViewById(R.id.EliminarBtn)
        update = findViewById(R.id.ModBtn)
        show = findViewById(R.id.MostrarBtn)
        showRecycler = findViewById(R.id.MostrarRecyclerBtn)
        searchRecycler = findViewById(R.id.BuscarBtn)

        create.setOnClickListener{
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }

        eliminate.setOnClickListener{
            val intent = Intent(this, DeleteActivity::class.java)
            startActivity(intent)
        }

        update.setOnClickListener{
            val intent = Intent(this, UpdateActivity::class.java)
            startActivity(intent)
        }

        show.setOnClickListener{
            val intent = Intent(this, ShowActivity::class.java)
            startActivity(intent)
        }

        showRecycler.setOnClickListener{
            val intent = Intent(this, ShowRecyclerActivity::class.java)
            startActivity(intent)
        }

        searchRecycler.setOnClickListener{
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }
}