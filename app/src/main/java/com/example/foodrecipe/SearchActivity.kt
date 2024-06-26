package com.example.foodrecipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipe.Adapters.CategoryWiseAdapter
import com.example.foodrecipe.Adapters.FavoriteAdapter
import com.example.foodrecipe.Adapters.SearchedMealAdapter
import com.example.foodrecipe.DataClass.Meal
import com.example.foodrecipe.DataClass.MealXX
import com.example.foodrecipe.ModelView.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    private lateinit var Imagevisibility: TextView
    private lateinit var Homemvvm: HomeViewModel
    private lateinit var editText: EditText
    private lateinit var imageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var Adapter: SearchedMealAdapter
    private lateinit var arr: ArrayList<Meal>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        arr= arrayListOf()
        Imagevisibility=findViewById(R.id.textVisibility)
        imageView=findViewById(R.id.ic_search)
        editText=findViewById(R.id.ed_search)
        recyclerView=findViewById(R.id.Recyclerforsearchedmeals)
        recyclerView.layoutManager=GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false)
        Adapter= SearchedMealAdapter()
        Homemvvm =ViewModelProvider(this)[HomeViewModel::class.java]
        var Searchjob: Job? =null
        editText.addTextChangedListener{query->
            Searchjob?.cancel()
            Searchjob = lifecycleScope.launch {
                delay(100)
                Homemvvm.getSearchedMeal(query.toString())
                getsearchmealresult()
            }

        }
        onclick()

    }

    private fun onclick() {
        Adapter.Clicked(object : SearchedMealAdapter.clicklistnersetter{
            override fun Onclicked(position: Int) {
                val curitem = arr[position]
                val intent = Intent(this@SearchActivity,LikeToEatActivity::class.java)
                intent.putExtra("MealId",curitem.idMeal)
                intent.putExtra("MeaLName",curitem.strMeal)
                intent.putExtra("MealImage",curitem.strMealThumb)
                startActivity(intent)
            }

        })
    }

    private fun getsearchmealresult() {
        Homemvvm.SearchedbyMealName().observe(this, Observer {meals->
            if(meals!=null && meals.isNotEmpty() && editText.text.toString()!!.isNotEmpty()) {
                arr = meals as ArrayList<Meal>
                Adapter.setData(meals as ArrayList<Meal>)
                recyclerView.adapter = Adapter
                Imagevisibility.isGone =true
                recyclerView.isGone = false
            }
            else{
                arr.clear()
                Adapter.setData(arr)
                recyclerView.adapter=Adapter
                Imagevisibility.isGone = false
                recyclerView.isGone= true
            }
        })
    }

    private fun getmeals() {
        if(editText.text.toString().isNotEmpty()){
            Homemvvm.getSearchedMeal(editText.text.toString())
        }
    }
}