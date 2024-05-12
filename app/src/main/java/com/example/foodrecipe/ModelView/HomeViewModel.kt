package com.example.foodrecipe.ModelView


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipe.DataClass.Meal
import com.example.foodrecipe.DataClass.MealList
import com.example.foodrecipe.MealApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel():ViewModel() {
    private var LiveMealData = MutableLiveData<Meal>()
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

    fun observelivedata():LiveData<Meal>{
        return LiveMealData
    }
}