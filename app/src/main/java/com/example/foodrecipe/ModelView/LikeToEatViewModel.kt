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

class LikeToEatViewModel(): ViewModel() {
    private var MealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id: String){
        val RetrofitBuilder = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
            .getMealDetail(id)
            .enqueue(object : Callback<MealList>{
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    if(response.body()!=null){
                        MealDetailLiveData.value = response.body()!!.meals[0]
                    }
                    else{
                        return
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

    }

    fun observeMealDetailLiveData(): LiveData<Meal>{
        return MealDetailLiveData
    }
}