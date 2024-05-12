package com.example.foodrecipe

import com.example.foodrecipe.DataClass.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetail(@Query("i")id: String): Call<MealList>
}