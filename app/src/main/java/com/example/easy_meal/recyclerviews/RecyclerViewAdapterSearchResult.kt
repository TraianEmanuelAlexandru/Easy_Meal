package com.example.easy_meal.recyclerviews

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

class RecyclerViewAdapterSearchResult(
    private val mContext: Context,
    private val mData: List<Recipe>
) : RecyclerView.Adapter<RecyclerViewAdapterSearchResult.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View
        val mInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.cardview_item_search_result, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv_recipe_title.text = mData[position].title
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
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_recipe_title: TextView
        var img_recipe_thumbnail: ImageView
        var cardView: CardView

        init {
            tv_recipe_title = itemView.findViewById(R.id.search_result_recipe_title)
            img_recipe_thumbnail = itemView.findViewById(R.id.search_result_recipe_img)
            cardView = itemView.findViewById(R.id.search_result_cardview)
        }
    }
}