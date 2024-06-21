package com.example.foodrecipe.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.DataClass.MealXX
import com.example.foodrecipe.R

class CategoryWiseAdapter(): RecyclerView.Adapter<CategoryWiseAdapter.MyViewHolder>() {
    private lateinit var Arraylist: ArrayList<MealXX>
    private lateinit var mListner : ClickListner

    interface ClickListner{
        fun OnClicked(position: Int)
    }
    fun settingClickListner(Listner: ClickListner){
        this.mListner = Listner
    }
    fun setData(NewArrayList : ArrayList<MealXX>){
        this.Arraylist = NewArrayList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.categorywise_recycler,parent,false)
        return MyViewHolder(itemView,mListner)
    }

    override fun getItemCount(): Int {
        return Arraylist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = Arraylist[position]
        Glide.with(holder.itemView).load(currentitem.strMealThumb).into(holder.image)
        holder.text.text = currentitem.strMeal
    }

    class MyViewHolder(itemView: View, listners:ClickListner): RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.imageViewRecyclerCategoryWise)
        val text: TextView = itemView.findViewById(R.id.tvCategorywise)
        init {
            itemView.setOnClickListener{
                listners.OnClicked(adapterPosition)
            }
        }
    }
}