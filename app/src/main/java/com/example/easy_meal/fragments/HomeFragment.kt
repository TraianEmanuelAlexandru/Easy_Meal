package com.example.easy_meal.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.easy_meal.R
import com.example.easy_meal.data.Recipe
import com.example.easy_meal.recyclerviews.RecyclerViewAdapter
import com.example.easy_meal.activities.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class HomeFragment : Fragment(), View.OnClickListener {

    private val lstRecipe: MutableList<Recipe> = ArrayList()
    private lateinit var searchRecipe: MutableList<Recipe>
    private var testArr: JSONArray? = null
    private lateinit var searchBtn: ImageButton
    private lateinit var breakfastBtn: Button
    private lateinit var lunchBtn: Button
    private lateinit var dinnerBtn: Button
    private lateinit var searchTv: TextView
    private lateinit var emptyView: TextView
    private lateinit var myrv: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val RootView = inflater.inflate(R.layout.fragment_home, container, false)
        val mToolbarContact = RootView.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(mToolbarContact)
        progressBar = RootView.findViewById(R.id.progressbar2)
        emptyView = RootView.findViewById(R.id.empty_view2)
        myrv = RootView.findViewById(R.id.recyclerview)
        myrv.layoutManager = GridLayoutManager(activity, 2)
        randomRecipes
        searchTv = RootView.findViewById(R.id.home_search_et)
        searchBtn = RootView.findViewById(R.id.home_search_btn)
        breakfastBtn = RootView.findViewById(R.id.home_breakfast_filter)
        lunchBtn = RootView.findViewById(R.id.home_lunch_filter)
        dinnerBtn = RootView.findViewById(R.id.home_dinner_filter)
        breakfastBtn.setOnClickListener(this)
        lunchBtn.setOnClickListener(this)
        dinnerBtn.setOnClickListener(this)
        searchBtn.setOnClickListener(this)
        searchTv.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text.toString() != "") {
                    emptyView.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    myrv.alpha = 0f
                    searchRecipe(v.text.toString())
                } else Toast.makeText(context, "Type something...", Toast.LENGTH_LONG).show()
            }
            false
        })
        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        return RootView
    }

    private fun searchRecipe(search: String) {
        searchRecipe = ArrayList()
        val URL =
            "https://api.spoonacular.com/recipes/search?query=$search&number=30&instructionsRequired=true&apiKey=e113f9009c4b4752b855a0239efc1e35"
        val requestQueue = Volley.newRequestQueue(activity)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,
            { response ->
                try {
                    testArr = response["results"] as JSONArray
                    for (i in 0 until testArr!!.length()) {
                        val jsonObject1: JSONObject = testArr!!.getJSONObject(i)
                        searchRecipe.add(
                            Recipe(
                                jsonObject1.optString("id"),
                                jsonObject1.optString("title"),
                                "https://spoonacular.com/recipeImages/" + jsonObject1.optString("image"),
                                jsonObject1.optString("servings").toInt(),
                                jsonObject1.optString("readyInMinutes").toInt()
                            )
                        )
                    }
                    progressBar.visibility = View.GONE
                    if (searchRecipe.isEmpty()) {
                        myrv.alpha = 0f
                        emptyView.visibility = View.VISIBLE
                    } else {
                        emptyView.visibility = View.GONE
                        val myAdapter = RecyclerViewAdapter(context, searchRecipe)
                        myrv.adapter = myAdapter
                        myrv.itemAnimator = DefaultItemAnimator()
                        myrv.alpha = 1f
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.i("the res is error:", error.toString()) }
        requestQueue.add(jsonObjectRequest)
    }

    private val randomRecipes: Unit
        private get() {
            val URL =
                " https://api.spoonacular.com/recipes/random?number=30&instructionsRequired=true&apiKey=e113f9009c4b4752b855a0239efc1e35"
            val requestQueue = Volley.newRequestQueue(activity)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                { response ->
                    try {
                        testArr = response["recipes"] as JSONArray
                        for (i in 0 until testArr!!.length()) {
                            val jsonObject1: JSONObject = testArr!!.getJSONObject(i)
                            lstRecipe.add(
                                Recipe(
                                    jsonObject1.optString("id"),
                                    jsonObject1.optString("title"),
                                    jsonObject1.optString("image"),
                                    jsonObject1.optString("servings").toInt(),
                                    jsonObject1.optString("readyInMinutes").toInt()
                                )
                            )
                        }
                        progressBar.visibility = View.GONE
                        val myAdapter = RecyclerViewAdapter(context, lstRecipe)
                        myrv.adapter = myAdapter
                        myrv.itemAnimator = DefaultItemAnimator()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            ) { error ->
                progressBar.visibility = View.GONE
                myrv.alpha = 0f
                emptyView.visibility = View.VISIBLE
            }
            requestQueue.add(jsonObjectRequest)
        }

    override fun onClick(v: View) {
        if (v === breakfastBtn) {
            searchRecipe("breakfast")
        } else if (v === lunchBtn) {
            searchRecipe("lunch")
        } else if (v === dinnerBtn) {
            searchRecipe("dinner")
        } else if (v === searchBtn) {
            try {
                val imm =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    activity?.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            } catch (e: Exception) {
            }
            if (searchTv.text.toString() != "") {
                progressBar.visibility = View.VISIBLE
                myrv.alpha = 0f
                searchRecipe(searchTv.text.toString())
            } else Toast.makeText(context, "Type something...", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.log_out, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.main_log_out) {
            FirebaseAuth.getInstance().signOut()
            sendToLogin()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendToLogin() {
        val loginIntent = Intent(activity, LoginActivity::class.java)
        startActivity(loginIntent)
        activity?.finish()
    }

}