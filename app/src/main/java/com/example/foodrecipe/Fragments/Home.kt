package com.example.foodrecipe.Fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.foodrecipe.DataClass.Meal
import com.example.foodrecipe.DataClass.MealList
import com.example.foodrecipe.LikeToEatActivity
import com.example.foodrecipe.MealApi
import com.example.foodrecipe.ModelView.HomeViewModel
import com.example.foodrecipe.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Home : Fragment() {

    private lateinit var image: ImageView
    private lateinit var Homemvvm: HomeViewModel
    private lateinit var RandomMeal: Meal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Homemvvm = ViewModelProvider(this)[HomeViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container ,false)

        image = view.findViewById(R.id.LiketoeatImage)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Homemvvm.getRandomMeal()
        ObserveRandomMeal()

        image.setOnClickListener {
            val intent = Intent(requireContext(), LikeToEatActivity::class.java)
            intent.putExtra("MealId", RandomMeal.idMeal)
            intent.putExtra("MeaLName", RandomMeal.strMeal)
            intent.putExtra("MealImage", RandomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun ObserveRandomMeal() {
        Homemvvm.observelivedata().observe(viewLifecycleOwner, object: Observer<Meal>{
            override fun onChanged(value: Meal) {
                Glide.with(requireContext()).load(value!!.strMealThumb)
                    .into(image)

                RandomMeal = value
            }

        })

    }

}


