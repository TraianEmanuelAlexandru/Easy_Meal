package com.example.easy_meal.recyclerviews

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easy_meal.R
import com.example.easy_meal.data.Ingredient
import com.squareup.picasso.Picasso

class RecyclerViewAdapterRecipeIngredient internal constructor(
    private val mContext: Context,
    private val mData: List<Ingredient>
) : RecyclerView.Adapter<RecyclerViewAdapterRecipeIngredient.MyViewHolder?>() {
    init {
        ingredientsList = ArrayList()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_ingredient_name: TextView
        var img_ingredient_thumbnail: ImageView

        init {
            tv_ingredient_name = itemView.findViewById(R.id.recipe_ingredient_name)
            img_ingredient_thumbnail = itemView.findViewById(R.id.recipe_ingredient_img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.item_ingredient, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv_ingredient_name.text = mData[position].name
        Picasso.get().load(mData[position].thumbnail).into(holder.img_ingredient_thumbnail)
    }

    override fun getItemCount(): Int {
        return mData.size
    }



    companion object {
        lateinit var ingredientsList: List<String>
    }
}