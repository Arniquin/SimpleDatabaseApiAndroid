package com.example.app_12

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ArticleAdapter(private val articles: ArrayList<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.ID_Field_Recycler_List)
        val name: TextView = view.findViewById(R.id.Name_Field_RecyclerList)
        val cost: TextView = view.findViewById(R.id.Cost_Field_RecyclerList)
        val date: TextView = view.findViewById(R.id.Date_Field_RecyclerList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_items, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.id.text = article.id.toString()
        holder.name.text = article.name
        holder.cost.text = article.cost.toString()
        holder.date.text = article.date
    }

    override fun getItemCount(): Int = articles.size
}
