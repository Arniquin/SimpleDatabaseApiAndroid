package com.example.app_12

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class DatabaseHandler(private val context: Context) {

    fun parseArticleFromJson(jsonObject: JSONObject): Article {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val id = jsonObject.optInt("article_id", -1)
        val name = jsonObject.optString("article_name", "")
        val cost = jsonObject.optDouble("article_cost", 0.0)
        val date = jsonObject.optString("article_date", null)

        return Article(id, name, cost, date)
    }


    fun fetchAllArticles(
        onSuccess: (ArrayList<Article>) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "http://10.0.2.2/ArticlesDatabaseApi/api.php"

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                val articles = ArrayList<Article>()
                for (i in 0 until response.length()) {
                    val articleJson = response.getJSONObject(i)
                    val article = parseArticleFromJson(articleJson)
                    articles.add(article)
                }
                onSuccess(articles)
            },
            { error ->
                onError(error.message ?: "Unknown error occurred")
                println("Unknown error occurred")
            }
        )

        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

    fun createArticle(
        article: Article,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        println("Funcion Llamadaaaa")
        val url = "http://10.0.2.2/ArticlesDatabaseApi/api.php"

        val params = JSONObject().apply {
            put("article_name", article.name)
            put("article_cost", article.cost)
        }

        val request = JsonObjectRequest(Request.Method.POST, url, params,
            { response ->
                val message = response.getString("message")
                val id = response.getInt("id")
                onSuccess(message)
            },
            { error ->
                onError(error.message ?: "Unknown error occurred")
            }
        )

        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

    fun updateArticle(
        article: Article,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "http://10.0.2.2/ArticlesDatabaseApi/api.php?id=${article.id}"

        val params = JSONObject().apply {
            put("article_name", article.name)
            put("article_cost", article.cost)
        }

        println("PUT Request URL: $url")


        val request = JsonObjectRequest(Request.Method.PUT, url, params,
            { response ->
                println("Raw Server Response: $response")
                onSuccess(response.optString("message"))
            },
            { error ->
                println("Raw Error Response: ${error.networkResponse?.data?.let { String(it) }}")
                onError(error.message ?: "Unknown error occurred")
            }
        )

        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

    fun deleteArticle(
        articleId: Int,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        val url = "http://10.0.2.2/ArticlesDatabaseApi/api.php?id=$articleId"

        val request = StringRequest(Request.Method.DELETE, url,
            { response ->
                onSuccess(response)
            },
            { error ->
                onError(error.message ?: "Unknown error occurred")
            }
        )

        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }
}
