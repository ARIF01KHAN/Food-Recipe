package com.example.foodrecipe.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.Adapters.CategoryAdapter
import com.example.foodrecipe.CategoryActivity
import com.example.foodrecipe.DataClass.Category
import com.example.foodrecipe.ModelView.CategoryViewModel
import com.example.foodrecipe.ModelView.HomeViewModel
import com.example.foodrecipe.R
import java.util.Locale

class Category : Fragment() {
    private lateinit var Categorymodel: HomeViewModel
    private lateinit var Arraylist: ArrayList<Category>
    private lateinit var adapter: CategoryAdapter
    private lateinit var Recycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Categorymodel = ViewModelProvider(this)[HomeViewModel::class.java]

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view1 = inflater.inflate(R.layout.fragment_category,container,false)
        Recycler= view1.findViewById(R.id.RecyclerforCategoryfragment)
        Arraylist = arrayListOf()
        Categorymodel.getAllCategoryList()
        adapter= CategoryAdapter()
        Recycler.layoutManager = GridLayoutManager(requireContext(),3,LinearLayoutManager.VERTICAL,false)
        observeCategorylistlivedata()
        oncategoryclicked()


        return view1
    }

    private fun oncategoryclicked() {
        adapter.setOnClickListner(object : CategoryAdapter.OnItemClick{
            override fun ItemClick(position: Int) {
                val curitem = Arraylist[position]
                val intent = Intent(requireContext(),CategoryActivity::class.java)
                intent.putExtra("CategoryName",curitem.strCategory)
                startActivity(intent)
            }

        })
    }

    private fun observeCategorylistlivedata() {
            Categorymodel.observeCategoryLiveData().observe(viewLifecycleOwner
            ) { Category ->
                Arraylist = Category as ArrayList<Category>
                adapter.setArrayList(Category as ArrayList<Category>)
                Recycler.adapter = adapter

            }




    }

}