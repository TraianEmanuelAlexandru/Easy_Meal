package com.example.easy_meal.activities

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.easy_meal.data.Ingredient
import com.example.easy_meal.R
import com.example.easy_meal.recyclerviews.RecyclerViewAdapterRecipeIngredient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

@Suppress("DEPRECATION")
class Recipe_Activity : AppCompatActivity() {
    private lateinit var title: TextView
    private lateinit var ready_in: TextView
    private lateinit var servings: TextView
    private lateinit var healthy: TextView
    private lateinit var instructions: TextView
    private lateinit var img: ImageView
    private lateinit var vegeterian: ImageView
    private lateinit var mRootRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var ingredientsArr: JSONArray
    private val ingredientsLst: MutableList<Ingredient> = ArrayList()
    private lateinit var myrv: RecyclerView
    private lateinit var fab: FloatingActionButton
    private var like = false

    private fun getPreferences(id: String, context: Context): String? {
        return context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE).getString(id, "null")
    }

    private fun savePreferences(id: String, context: Context) {
        val sharedPref = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(id, id)
        editor.apply()
    }

    private fun deletePreferences(id: String, context: Context) {
        val sharedPref = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove(id)
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        val intent = intent
        val recipeId = intent.extras?.getString("id")
        mAuth = FirebaseAuth.getInstance()
        val uid = mAuth.currentUser?.uid
        mRootRef = FirebaseDatabase.getInstance().reference.child(uid.toString()).child(recipeId!!)
        img = findViewById(R.id.recipe_img)
        title = findViewById(R.id.recipe_title)
        ready_in = findViewById(R.id.recipe_ready_in)
        servings = findViewById(R.id.recipe_servings)
        healthy = findViewById(R.id.recipe_healthy)
        vegeterian = findViewById(R.id.recipe_vegetarian)
        instructions = findViewById(R.id.recipe_instructions)
        fab = findViewById(R.id.floatingActionButton)

        getRecipeData(recipeId)

        if (getPreferences(recipeId, applicationContext) != "null") {
            fab.setImageResource(R.drawable.ic_favorite_black_24dp)
        }

        fab.setOnClickListener {
            if (getPreferences(recipeId, applicationContext) != "null") {
                deletePreferences(recipeId,applicationContext)
                fab.setImageResource(R.drawable.ic_favorite_border_black_24dp)
        } else {
                savePreferences(recipeId,applicationContext)
                fab.setImageResource(R.drawable.ic_favorite_black_24dp)
        }
        }
        myrv = findViewById(R.id.recipe_ingredients_rv)
        myrv.layoutManager = GridLayoutManager(this, 2)
    }

    @SuppressLint("SetTextI18n")
    private fun getRecipeData(recipeId: String?) {
        val URL =
            " https://api.spoonacular.com/recipes/$recipeId/information?apiKey=e113f9009c4b4752b855a0239efc1e35"
        val requestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,
            { response ->
                try {
                    try {
                        Picasso.get().load(response["image"] as String).into(img)
                    } catch (e: Exception) {
                        img.setImageResource(R.drawable.nopicture)
                    }
                    title.text = response["title"] as String
                    ready_in.text = Integer.toString((response["readyInMinutes"] as Int))
                    servings.text = Integer.toString((response["servings"] as Int))
                    if (response["veryHealthy"] as Boolean) {
                        healthy.text = "Healthy"
                    }
                    if (response["vegetarian"] as Boolean) {
                        vegeterian.setImageResource(R.drawable.vegeterian)
                    }
                    try {
                        if (response["instructions"] == "") {
                            throw Exception("No Instructions")
                        } else instructions.text =
                            Html.fromHtml(response["instructions"] as String)
                    } catch (e: Exception) {
                        val msg =
                            "Unfortunately, the recipe you were looking for not found, to view the original recipe click on the link below:" + "<a href=" + response["spoonacularSourceUrl"] + ">" + response["spoonacularSourceUrl"] + "</a>"
                        instructions.movementMethod = LinkMovementMethod.getInstance()
                        instructions.text = Html.fromHtml(msg)
                    }
                    ingredientsArr = response["extendedIngredients"] as JSONArray
                    for (i in 0 until ingredientsArr.length()) {
                        var jsonObject1: JSONObject
                        jsonObject1 = ingredientsArr.getJSONObject(i)
                        ingredientsLst.add(
                            Ingredient(
                                jsonObject1.optString("originalString"),
                                jsonObject1.optString("image")
                            )
                        )
                    }
                    val myAdapter =
                        RecyclerViewAdapterRecipeIngredient(applicationContext, ingredientsLst)
                    myrv.adapter = myAdapter
                    myrv.itemAnimator = DefaultItemAnimator()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.i("the res is error:", error.toString()) }
        requestQueue.add(jsonObjectRequest)
    }
}


