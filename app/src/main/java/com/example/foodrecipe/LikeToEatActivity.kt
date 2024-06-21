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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodrecipe.DataClass.Meal
import com.example.foodrecipe.DataClass.MealX
import com.example.foodrecipe.ModelView.LikeToEatViewModel
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

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
    private lateinit var floataction : ConstraintLayout
    private lateinit var databaseReference: DatabaseReference

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
        databaseReference= FirebaseDatabase.getInstance().getReference("UserID")
        floataction = findViewById(R.id.fav_constraint_button)
        loadingcase()
        mealMvvm = ViewModelProvider(this)[LikeToEatViewModel::class.java]
        val email = FirebaseAuth.getInstance().currentUser?.email.toString()
        val intent = getIntent()
        Mealid = intent.getStringExtra("MealId").toString()
        MealName = intent.getStringExtra("MeaLName").toString()
        MealImage = intent.getStringExtra("MealImage").toString()

        mealMvvm.getMealDetails(Mealid)
        val meal_id = MealX(Mealid,MealName,MealImage)
        floataction.setOnClickListener{
                databaseReference.child(email.removeSuffix("@gmail.com")).child(Mealid).setValue(meal_id)
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
        }

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