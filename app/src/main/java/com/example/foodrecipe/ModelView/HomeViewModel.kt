package com.example.foodrecipe.ModelView


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipe.DataClass.Category
import com.example.foodrecipe.DataClass.CategoryMealList
import com.example.foodrecipe.DataClass.Meal
import com.example.foodrecipe.DataClass.MealLisWithArea
import com.example.foodrecipe.DataClass.MealList
import com.example.foodrecipe.DataClass.MealX
import com.example.foodrecipe.MealApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class HomeViewModel():ViewModel() {
    private var LiveMealData = MutableLiveData<Meal>()
    private var LivePopularMeal= MutableLiveData<List<MealX>>()
    private var LiveCategoryList= MutableLiveData<List<Category>>()
    fun getRandomMeal() {
        val RetrofitBuilder = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)

        val retrofit = RetrofitBuilder.getRandomMeal()


        retrofit.enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal = response.body()!!.meals[0]
                    LiveMealData.value = randomMeal
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {

            }

        })
    }

    fun getApiData(){
        val retrofitbuilder= Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
            .getPopularItem("Indian")
            .enqueue(object : Callback<MealLisWithArea>{
                override fun onResponse(
                    call: Call<MealLisWithArea>,
                    response: Response<MealLisWithArea>,
                ) {
                    if(response.body()!=null){
                        LivePopularMeal.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealLisWithArea>, t: Throwable) {

                }
            })
    }

    fun getAllCategoryList(){
        val retbuild = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
            .getCatagoryList()
            .enqueue(object : Callback<CategoryMealList>{
                override fun onResponse(
                    call: Call<CategoryMealList>,
                    response: Response<CategoryMealList>,
                ) {
                    if(response.body()!=null){
                        LiveCategoryList.value= response.body()!!.categories
                    }
                }

                override fun onFailure(call: Call<CategoryMealList>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    fun observelivedata():LiveData<Meal>{
        return LiveMealData
    }

    fun ObserveAreaWiseMealLiveData():LiveData<List<MealX>>{
        return LivePopularMeal
    }

    fun observeCategoryLiveData(): LiveData<List<Category>>{
        return LiveCategoryList
    }
}