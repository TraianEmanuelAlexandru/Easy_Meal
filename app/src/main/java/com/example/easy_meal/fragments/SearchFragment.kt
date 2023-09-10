package com.example.easy_meal.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easy_meal.data.Ingredient
import com.example.easy_meal.R
import com.example.easy_meal.recyclerviews.RecyclerViewAdapterIngredient
import com.example.easy_meal.activities.LoginActivity
import com.example.easy_meal.activities.SearchResultsActivity
import com.google.firebase.auth.FirebaseAuth


class SearchFragment : Fragment() {

    private val lstIngredient: MutableList<Ingredient> = ArrayList()
    private lateinit var myrv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initializeIngredients()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val RootView = inflater.inflate(R.layout.fragment_search, container, false)
        val mToolbarContact = RootView.findViewById<Toolbar>(R.id.toolbar_search)
        (activity as AppCompatActivity).setSupportActionBar(mToolbarContact)
        myrv = RootView.findViewById(R.id.recycleview_ingredients)
        myrv.layoutManager = GridLayoutManager(activity, 3)
        val myAdapter = RecyclerViewAdapterIngredient(requireContext(), lstIngredient)
        myrv.adapter = myAdapter
        val searchIngredients = RootView.findViewById<Button>(R.id.ingredients_search)
        searchIngredients.setOnClickListener {
            val tmp: List<String> = RecyclerViewAdapterIngredient.ingredientsList
            if (tmp.isEmpty()) {
                Toast.makeText(
                    activity,
                    "You must select at least one ingredient",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val searchResultsIntent = Intent(activity, SearchResultsActivity::class.java)
                startActivity(searchResultsIntent)
            }
        }
        return RootView
    }

    private fun initializeIngredients() {
        lstIngredient.add(Ingredient("Beef", "beef-cubes-raw.png"))
        lstIngredient.add(Ingredient("Fish", "fish-fillet.jpg"))
        lstIngredient.add(Ingredient("Chicken", "chicken-breasts.png"))
        lstIngredient.add(Ingredient("Tuna", "canned-tuna.png"))
        lstIngredient.add(Ingredient("Flour", "flour.png"))
        lstIngredient.add(Ingredient("Rice", "uncooked-white-rice.png"))
        lstIngredient.add(Ingredient("Pasta", "fusilli.jpg"))
        lstIngredient.add(Ingredient("Cheese", "cheddar-cheese.png"))
        lstIngredient.add(Ingredient("Butter", "butter.png"))
        lstIngredient.add(Ingredient("Bread", "white-bread.jpg"))
        lstIngredient.add(Ingredient("Onion", "brown-onion.png"))
        lstIngredient.add(Ingredient("Garlic", "garlic.jpg"))
        lstIngredient.add(Ingredient("Milk", "milk.png"))
        lstIngredient.add(Ingredient("Eggs", "egg.png"))
        lstIngredient.add(Ingredient("Oil", "vegetable-oil.jpg"))
        lstIngredient.add(Ingredient("Yogurt", "plain-yogurt.jpg"))
        lstIngredient.add(Ingredient("Salt", "salt.jpg"))
        lstIngredient.add(Ingredient("Sugar", "sugar-in-bowl.png"))
        lstIngredient.add(Ingredient("Pepper", "pepper.jpg"))
        lstIngredient.add(Ingredient("Water", "water.jpg"))
        lstIngredient.add(Ingredient("Parsley", "parsley.jpg"))
        lstIngredient.add(Ingredient("Basil", "basil.jpg"))
        lstIngredient.add(Ingredient("Chocolate", "milk-chocolate.jpg"))
        lstIngredient.add(Ingredient("Nuts", "hazelnuts.png"))
        lstIngredient.add(Ingredient("Tomato", "tomato.png"))
        lstIngredient.add(Ingredient("Cucumber", "cucumber.jpg"))
        lstIngredient.add(Ingredient("Bell pepper", "red-bell-pepper.jpg"))
        lstIngredient.add(Ingredient("Mushrooms", "portabello-mushrooms.jpg"))
        lstIngredient.add(Ingredient("Lemon", "lemon.jpg"))
        lstIngredient.add(Ingredient("Orange", "orange.jpg"))
        lstIngredient.add(Ingredient("Banana", "bananas.jpg"))
        lstIngredient.add(Ingredient("Wine", "red-wine.jpg"))
        lstIngredient.add(Ingredient("Apple", "apple.jpg"))
        lstIngredient.add(Ingredient("Berries", "berries-mixed.jpg"))
        lstIngredient.add(Ingredient("Biscuits", "buttermilk-biscuits.jpg"))
        lstIngredient.add(Ingredient("Pineapple", "pineapple.jpg"))
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


