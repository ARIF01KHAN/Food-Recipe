package com.example.foodrecipe.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.DataClass.FavoriteDataClass
import com.example.foodrecipe.DataClass.MealX
import com.example.foodrecipe.R

class FavoriteAdapter(val array : ArrayList<FavoriteDataClass>): RecyclerView.Adapter<FavoriteAdapter.MyviewHolder>() {
    private lateinit var mListner: ClickListnerSetter

    fun OnitemClicked(listner: ClickListnerSetter){
        this.mListner = listner
    }
    interface ClickListnerSetter{
        fun OnClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.favorite_recyclerviews_etter, parent ,false )
        return MyviewHolder(itemView,mListner)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        var currentitem = array[position]
        Glide.with(holder.itemView).load(currentitem.strMealThumb).into(holder.image)
        holder.text.text = currentitem.strMeal

    }
    class MyviewHolder(itemView: View,Listner: ClickListnerSetter): RecyclerView.ViewHolder(itemView)  {
        val image = itemView.findViewById<ImageView>(R.id.imageViewRecyclerFavoriteWise)
        val text: TextView = itemView.findViewById(R.id.tvFavoritewise)

        init{
            itemView.setOnClickListener{
                Listner.OnClicked(adapterPosition)
            }
        }
    }
}