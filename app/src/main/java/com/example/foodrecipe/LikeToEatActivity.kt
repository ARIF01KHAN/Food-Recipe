package com.example.foodrecipe

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodrecipe.DataClass.Meal
import com.example.foodrecipe.ModelView.LikeToEatViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

class LikeToEatActivity : AppCompatActivity() {

    private lateinit var MealName:String
    private lateinit var Image:ImageView
    private lateinit var CollToolBar: CollapsingToolbarLayout
    private lateinit var Introduction: TextView
    private lateinit var Mealid: String
    private lateinit var MealImage: String
    private lateinit var mealMvvm: LikeToEatViewModel
    private lateinit var TextCatogory: TextView
    private lateinit var TextArea: TextView
    private lateinit var ProgressBar: ProgressBar
    private lateinit var FavButton: ConstraintLayout
    private lateinit var YtImage: ImageView
    private lateinit var YtURI: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_like_to_eat)

        Image = findViewById(R.id.ImageMealEat)
        CollToolBar = findViewById(R.id.CollapsingToolBar)
        Introduction = findViewById((R.id.Instruction1))
        TextCatogory= findViewById(R.id.tv_category1)
        TextArea = findViewById(R.id.tv_Area)
        ProgressBar = findViewById(R.id.Progressbar)
        FavButton =findViewById(R.id.fav_constraint_button)
        YtImage = findViewById(R.id.img_youtube)
        loadingcase()
        mealMvvm = ViewModelProviders.of(this)[LikeToEatViewModel::class.java]

        val intent = getIntent()
        Mealid = intent.getStringExtra("MealId").toString()
        MealName = intent.getStringExtra("MeaLName").toString()
        MealImage = intent.getStringExtra("MealImage").toString()

        mealMvvm.getMealDetails(Mealid)

        YtImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(YtURI))
            startActivity(intent)
        }

        ObserveMealDetailsLiveData()

        setData()
    }

    private fun ObserveMealDetailsLiveData() {
        OnresponseCase()
        mealMvvm.observeMealDetailLiveData().observe(this, object : Observer<Meal>{
            override fun onChanged(value: Meal) {
                val meal = value
                TextCatogory.text = meal.strCategory
                TextArea.text = meal.strArea
                Introduction.text = meal.strInstructions
                YtURI = meal.strYoutube

            }
        })
    }

    private fun setData() {
        Glide.with(this).load(MealImage).into(Image)

        CollToolBar.title = MealName


    }

    private fun loadingcase(){
        YtImage.visibility = View.INVISIBLE
        FavButton.visibility = View.INVISIBLE
        Introduction.visibility = View.INVISIBLE
        ProgressBar.visibility = View.VISIBLE
        TextArea.visibility = View.INVISIBLE
        TextCatogory.visibility = View.INVISIBLE
    }

    private fun OnresponseCase(){
        YtImage.visibility = View.VISIBLE
        FavButton.visibility = View.VISIBLE
        Introduction.visibility = View.VISIBLE
        ProgressBar.visibility = View.INVISIBLE
        TextArea.visibility = View.VISIBLE
        TextCatogory.visibility = View.VISIBLE
    }
}