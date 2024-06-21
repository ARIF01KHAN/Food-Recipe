package com.example.foodrecipe.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.DataClass.MealX
import com.example.foodrecipe.R

class AreaWiseMealAdapter(): RecyclerView.Adapter<AreaWiseMealAdapter.MyViewHolder>() {
    private lateinit var Arraylist: ArrayList<MealX>
    private lateinit var mlistner:OnClickResponse
    interface OnClickResponse{
        fun OnClicked(position: Int)
    }

    fun Clicked(listner:OnClickResponse){
        this.mlistner=listner
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.popular_meals, parent, false)
        return MyViewHolder(itemView,mlistner)
    }

    fun setValue(NewArray: ArrayList<MealX>){
        this.Arraylist =NewArray
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return Arraylist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = Arraylist[position]

        Glide.with(holder.itemView).load(Uri.parse(currentItem.strMealThumb))
            .into(holder.image)
    }

    class MyViewHolder(itemView: View,listner: OnClickResponse): RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.PopularMeal)
        init{
            itemView.setOnClickListener{
                listner.OnClicked(adapterPosition)
            }
        }
    }
}