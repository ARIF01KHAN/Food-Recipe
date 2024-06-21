package com.example.foodrecipe.ModelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipe.DataClass.CategoryWiseData
import com.example.foodrecipe.DataClass.MealXX
import com.example.foodrecipe.MealApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryViewModel: ViewModel() {
    private var liveCayegoryWise = MutableLiveData<List<MealXX>>()
    fun getCetegoriesData(CategoryName: String){
        val retrofitbuilder = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
            .getCategoryWiseMealList(CategoryName)
            .enqueue(object : Callback<CategoryWiseData>{
                override fun onResponse(
                    call: Call<CategoryWiseData>,
                    response: Response<CategoryWiseData>,
                ) {
                    if(response.body()!= null){
                        liveCayegoryWise.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<CategoryWiseData>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun getCategorywiseData(): LiveData<List<MealXX>>{
        return liveCayegoryWise
    }
}