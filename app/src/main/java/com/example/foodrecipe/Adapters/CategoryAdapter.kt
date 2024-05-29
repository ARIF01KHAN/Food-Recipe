package com.example.foodrecipe.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.DataClass.Category
import com.example.foodrecipe.R

class CategoryAdapter():RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {
    private lateinit var mListener: OnItemClick
    private lateinit var ArrayList: ArrayList<Category>
    interface OnItemClick{
        fun ItemClick(position: Int)
    }

    fun setOnClickListner(Listner: OnItemClick){
        mListener = Listner
    }
    fun setArrayList(NewArrayList: ArrayList<Category>){
        this.ArrayList = NewArrayList
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category, parent, false)
        return MyViewHolder(itemView,mListener)
    }

    override fun getItemCount(): Int {
        return ArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var CurrentItem = ArrayList[position]
        Glide.with(holder.itemView).load(Uri.parse(CurrentItem.strCategoryThumb)).into(holder.image)
        holder.CategoryName.text = CurrentItem.strCategory

    }

    class MyViewHolder(itemView: View, listner : OnItemClick): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageCategory)
        val CategoryName: TextView = itemView.findViewById(R.id.CategoryName)

        init {
            itemView.setOnClickListener{
                listner.ItemClick(adapterPosition)
            }
        }

    }
}