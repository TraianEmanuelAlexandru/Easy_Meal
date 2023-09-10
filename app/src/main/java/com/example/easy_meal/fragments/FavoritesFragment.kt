package com.example.easy_meal.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
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
import org.json.JSONException

class FavoritesFragment : Fragment() {
    private lateinit var title: TextView
    private lateinit var fav: RecyclerView
    private val lstFavorites: MutableList<Recipe> = ArrayList()

    private fun getAllPreferences(context: Context, callback: (result: String) -> Unit) {
        val sharedPref = context.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
        if (sharedPref.all.isNotEmpty())
            for (keys in sharedPref.all)
                callback(keys.key)
        else
            callback("void")
    }

    private fun getResultFavorite(context: Context,recipeId: String) {
        val URL =
            " https://api.spoonacular.com/recipes/$recipeId/information?apiKey=e113f9009c4b4752b855a0239efc1e35"
        val requestQueue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            URL,
            null,
            { response ->
                try {
                    lstFavorites.add(
                        Recipe(
                            response.optString("id"),
                            response.optString("title"),
                            response.optString("image"),
                            response.optString("servings").toInt(),
                            response.optString("readyInMinutes").toInt()
                        )
                    )
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        ) { error -> Log.i("the res is error:", error.toString()) }
        requestQueue.add(jsonObjectRequest)
        requestQueue.addRequestFinishedListener<RecyclerViewAdapter>(){
            val myAdapter = RecyclerViewAdapter(view?.context, lstFavorites)
            fav.adapter = myAdapter
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        title = view.findViewById(R.id.titleFavorite)
        fav = view.findViewById(R.id.recyclerview_favorites)
        fav.layoutManager = GridLayoutManager(activity, 2)

        // Ritorna un preferito alla volta e lo inserisce in un ArrayList
        // Se non ci sono preferiti viene settato un title che lo riferisce

        getAllPreferences(view.context) { id ->
            if (id != "void") {
                getResultFavorite(view.context,id)
                val myAdapter = RecyclerViewAdapter(view.context, lstFavorites)
                fav.adapter = myAdapter
            } else {
                title.text = "Non hai nessun piatto preferito"
            }
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
