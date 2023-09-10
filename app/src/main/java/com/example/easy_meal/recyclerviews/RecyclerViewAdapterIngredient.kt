package com.example.easy_meal.recyclerviews

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.easy_meal.R
import com.example.easy_meal.data.Ingredient
import com.squareup.picasso.Picasso

class RecyclerViewAdapterIngredient internal constructor(
    private val mContext: Context,
    private val mData: List<Ingredient>
) : RecyclerView.Adapter<RecyclerViewAdapterIngredient.MyViewHolder>() {
    init {
        ingredientsList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.cardview_item_ingredient, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv_ingredient_name.text = mData[position].name
        Picasso.get().load(mData[position].thumbnail).into(holder.img_ingredient_thumbnail)
        if (mData[position].isSelected) holder.cardView.setCardBackgroundColor(Color.parseColor("#EAEDED")) else holder.cardView.setCardBackgroundColor(
            Color.WHITE
        )
        holder.cardView.setOnClickListener {
            if (!mData[position].isSelected) {
                holder.cardView.setCardBackgroundColor(Color.parseColor("#EAEDED"))
                ingredientsList.add(mData[position].name)
            } else if (mData[position].isSelected) {
                holder.cardView.setCardBackgroundColor(Color.WHITE)
                ingredientsList.remove(mData[position].name)
            }
            mData[position].setSelected()
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_ingredient_name: TextView
        var img_ingredient_thumbnail: ImageView
        var layoutl: LinearLayout
        var cardView: CardView

        init {
            tv_ingredient_name = itemView.findViewById<View>(R.id.ingredient_name) as TextView
            img_ingredient_thumbnail = itemView.findViewById<View>(R.id.ingredient_img) as ImageView
            cardView = itemView.findViewById<View>(R.id.cardview_ingredient) as CardView
            layoutl = itemView.findViewById(R.id.ingredient_layout)
        }
    }

    companion object {
        lateinit var ingredientsList: MutableList<String>
    }
}