package com.example.foodrecipe.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.DataClass.Meal
import com.example.foodrecipe.R

class SearchedMealAdapter():RecyclerView.Adapter<SearchedMealAdapter.MyViewHolder>() {
    private lateinit var arr: ArrayList<Meal>
    private lateinit var mListner: clicklistnersetter

    fun Clicked(listner:clicklistnersetter){
        this.mListner=listner
    }
    interface clicklistnersetter{
        fun Onclicked(position: Int)
    }
    fun setData(array: ArrayList<Meal>){
        this.arr= array
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.categorywise_recycler,parent,false)
        return MyViewHolder(itemview,mListner)
    }

    override fun getItemCount(): Int {
        return arr.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val curit= arr[position]
        Glide.with(holder.itemView).load(curit.strMealThumb.toString()).into(holder.image)
        holder.textView.text=curit.strMeal
    }
    class MyViewHolder(itemview: View,mListner: clicklistnersetter): RecyclerView.ViewHolder(itemview) {
        val image = itemview.findViewById<ImageView>(R.id.imageViewRecyclerCategoryWise)
        val textView:TextView = itemView.findViewById(R.id.tvCategorywise)

        init{
            itemView.setOnClickListener{
                mListner.Onclicked(adapterPosition)
            }
        }

    }
}