package com.example.easy_meal.recyclerviews

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.easy_meal.R
import com.example.easy_meal.activities.Recipe_Activity
import com.example.easy_meal.data.Recipe
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val mContext: Context?, private val mData: List<Recipe>) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.cardview_item_recipe, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv_recipe_title.text = mData[position].title
        holder.tv_amount_of_dishes.text = mData[position].amountOfDishes.toString()
        holder.tv_ready_in_mins.text = mData[position].readyInMins.toString()
        if (mData[position].thumbnail!!.isEmpty()) {
            holder.img_recipe_thumbnail.setImageResource(R.drawable.nopicture)
        } else {
            Picasso.get().load(mData[position].thumbnail).into(holder.img_recipe_thumbnail)
        }
        holder.cardView.setOnClickListener {
            val intent = Intent(mContext, Recipe_Activity::class.java)
            intent.putExtra("id", mData[position].id)
            intent.putExtra("title", mData[position].title)
            intent.putExtra("img", mData[position].thumbnail)
            mContext?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_recipe_title: TextView
        var tv_amount_of_dishes: TextView
        var tv_ready_in_mins: TextView
        var img_recipe_thumbnail: ImageView
        var cardView: CardView

        init {
            tv_recipe_title = itemView.findViewById<View>(R.id.recipe_title_id) as TextView
            img_recipe_thumbnail = itemView.findViewById<View>(R.id.recipe_img_id) as ImageView
            tv_amount_of_dishes = itemView.findViewById<View>(R.id.servingTvLeft) as TextView
            tv_ready_in_mins = itemView.findViewById<View>(R.id.readyInTvRight) as TextView
            cardView = itemView.findViewById<View>(R.id.cardview_id) as CardView
        }
    }
}