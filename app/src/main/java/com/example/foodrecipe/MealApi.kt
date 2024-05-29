package com.example.foodrecipe
import com.example.foodrecipe.DataClass.CategoryMealList
import com.example.foodrecipe.DataClass.MealLisWithArea
import com.example.foodrecipe.DataClass.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getMealDetail(@Query("i")id: String): Call<MealList>

    @GET("filter.php?")
    fun getPopularItem(@Query("a")AreaName:String): Call<MealLisWithArea>

    @GET("categories.php")
    fun getCatagoryList(): Call<CategoryMealList>
}