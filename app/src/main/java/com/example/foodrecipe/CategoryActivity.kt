package com.example.foodrecipe

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.Adapters.CategoryWiseAdapter
import com.example.foodrecipe.DataClass.CategoryWiseData
import com.example.foodrecipe.DataClass.MealXX
import com.example.foodrecipe.ModelView.CategoryViewModel

class CategoryActivity : AppCompatActivity() {

    private lateinit var CategoryMvvm : CategoryViewModel
    private lateinit var Recycler: RecyclerView
    private lateinit var adapter :CategoryWiseAdapter
    private lateinit var text: String
    private lateinit var array: ArrayList<MealXX>
    private lateinit var text1:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        text = intent.getStringExtra("CategoryName").toString()
        setContentView(R.layout.activity_category)
        Recycler = findViewById(R.id.categorywiseRecycler)
        Recycler.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL,false)
        adapter = CategoryWiseAdapter()
        CategoryMvvm= ViewModelProvider(this)[CategoryViewModel::class.java]
        CategoryMvvm.getCetegoriesData(text)
        text1 = findViewById(R.id.txtview)
        getdata()
        observeClickOnCategoryListitem()

    }

    private fun observeClickOnCategoryListitem() {
        adapter.settingClickListner(object : CategoryWiseAdapter.ClickListner{
            override fun OnClicked(position: Int) {
                val intent = Intent(this@CategoryActivity,LikeToEatActivity::class.java)
                intent.putExtra("MealId", array[position].idMeal)
                intent.putExtra("MeaLName", array[position].strMeal)
                intent.putExtra("MealImage", array[position].strMealThumb)
                startActivity(intent)

            }

        })
    }

    private fun getdata() {
        CategoryMvvm.getCategorywiseData().observe(this,object: Observer<List<MealXX>>{
            override fun onChanged(value: List<MealXX>) {
                adapter.setData(value as ArrayList<MealXX>)
                array = value as ArrayList<MealXX>
                Recycler.adapter = adapter
                text1.text= array.size.toString()
            }

        })
    }
}

