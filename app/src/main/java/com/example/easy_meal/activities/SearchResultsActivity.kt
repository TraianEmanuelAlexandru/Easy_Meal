package com.example.easy_meal.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.easy_meal.R
import com.example.easy_meal.data.Recipe
import com.example.easy_meal.recyclerviews.RecyclerViewAdapterIngredient
import com.example.easy_meal.recyclerviews.RecyclerViewAdapterSearchResult
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var ingredients_list: TextView
    private lateinit var myrv: RecyclerView
    private lateinit var testArr: JSONArray
    private val lstRecipe: MutableList<Recipe> = ArrayList()
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        ingredients_list = findViewById(R.id.ingredients_names_list)
        progressBar = findViewById(R.id.progressbar3)
        val searchText = getStringFromList(RecyclerViewAdapterIngredient.ingredientsList)
        ingredients_list.text = searchText
        getResults(searchText)
    }

    private fun getResults(searchText: String) {
        myrv = findViewById(R.id.recycleview_ingredients_search_result)
        myrv.layoutManager = GridLayoutManager(this, 2)
        val URL =
            "https://api.spoonacular.com/recipes/findByIngredients?ingredients=$searchText&number=30&instructionsRequired=true&apiKey=e113f9009c4b4752b855a0239efc1e35"
        val requestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET,
            URL,
            null,
            { response ->
                try {
                    testArr = response
                    Log.i("the res is:", testArr.toString())
                    for (i in 0 until testArr.length()) {
                        var jsonObject1: JSONObject
                        jsonObject1 = testArr.getJSONObject(i)
                        lstRecipe.add(
                            Recipe(
                                jsonObject1.optString("id"),
                                jsonObject1.optString("title"),
                                jsonObject1.optString("image"),
                                0,
                                0
                            )
                        )
                    }
                    progressBar.visibility = View.GONE
                    val myAdapter = RecyclerViewAdapterSearchResult(applicationContext, lstRecipe)
                    myrv.adapter = myAdapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.i("the res is error:", error.toString()) }
        requestQueue.add(jsonObjectRequest)
    }

    private fun getStringFromList(ingredientsList: List<String>): String {
        val result = StringBuilder(ingredientsList[0])
        for (i in 1 until ingredientsList.size) {
            result.append(", ").append(ingredientsList[i])
        }
        return result.toString()
    }

}