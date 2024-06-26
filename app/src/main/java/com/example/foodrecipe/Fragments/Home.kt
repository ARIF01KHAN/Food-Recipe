package com.example.foodrecipe.Fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodrecipe.Adapters.AreaWiseMealAdapter
import com.example.foodrecipe.Adapters.CategoryAdapter
import com.example.foodrecipe.CategoryActivity
import com.example.foodrecipe.DataClass.Category
import com.example.foodrecipe.DataClass.Meal
import com.example.foodrecipe.DataClass.MealX
import com.example.foodrecipe.LikeToEatActivity
import com.example.foodrecipe.MainActivity
import com.example.foodrecipe.ModelView.HomeViewModel
import com.example.foodrecipe.R
import com.example.foodrecipe.SearchActivity
import com.google.firebase.auth.FirebaseAuth

class Home : Fragment() {

    private lateinit var image: ImageView
    private lateinit var Homemvvm: HomeViewModel
    private lateinit var RandomMeal: Meal
    private lateinit var Arraylist: ArrayList<Category>
    private lateinit var Recycler: RecyclerView
    private lateinit var adapter1: AreaWiseMealAdapter
    private lateinit var Recycler1: RecyclerView
    private lateinit var adapter2: CategoryAdapter
    private lateinit var AreawiseArray: ArrayList<MealX>
    private lateinit var image1: ImageView
    private lateinit var ic_searchbutton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Homemvvm = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        ic_searchbutton = view.findViewById(R.id.ic_searchbutton)
        Recycler = view.findViewById(R.id.RecyclerOverPopularMeal)
        Recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        image = view.findViewById(R.id.LiketoeatImage)
        adapter1 = AreaWiseMealAdapter()
        image1 = view.findViewById(R.id.logoutbutton)

        Recycler1 = view.findViewById(R.id.recycler_view)
        Recycler1.layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        adapter2 = CategoryAdapter()
        image1.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent1=Intent(requireContext(),MainActivity::class.java)
            startActivity(intent1)
            activity?.finish()
        }
        ic_searchbutton.setOnClickListener {
            val intent = Intent(requireContext(),SearchActivity::class.java)
            startActivity(intent)

        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ObserveRandomMeal()

        Homemvvm.getApiData()
        ObserveAreaWiseMeal()

        Homemvvm.getAllCategoryList()
        ObserveCategoryList()

        image.setOnClickListener {
            val intent = Intent(requireContext(), LikeToEatActivity::class.java)
            intent.putExtra("MealId", RandomMeal.idMeal)
            intent.putExtra("MeaLName", RandomMeal.strMeal)
            intent.putExtra("MealImage", RandomMeal.strMealThumb)
            startActivity(intent)
        }

        observeClickonCategory()
        observeClickonAreaWise()
    }

    private fun observeClickonAreaWise() {
        adapter1.Clicked(object : AreaWiseMealAdapter.OnClickResponse {
            override fun OnClicked(position: Int) {
                val currentitem = AreawiseArray[position]
                val intent = Intent(requireContext(), LikeToEatActivity::class.java)
                intent.putExtra("MealId", currentitem.idMeal)
                intent.putExtra("MeaLName", currentitem.strMeal)
                intent.putExtra("MealImage", currentitem.strMealThumb)
                startActivity(intent)
            }
        })
    }

    private fun observeClickonCategory() {
        adapter2.setOnClickListner(object : CategoryAdapter.OnItemClick {
            override fun ItemClick(position: Int) {
                val intent = Intent(requireContext(), CategoryActivity::class.java)
                intent.putExtra("CategoryName", Arraylist[position].strCategory)
                startActivity(intent)
            }
        })
    }

    private fun ObserveCategoryList() {
        Homemvvm.observeCategoryLiveData().observe(viewLifecycleOwner) { Category ->
            Arraylist = Category as ArrayList<Category>
            adapter2.setArrayList(Category as ArrayList<Category>)
            Recycler1.adapter = adapter2
        }
    }

    private fun ObserveAreaWiseMeal() {
        Homemvvm.ObserveAreaWiseMealLiveData().observe(viewLifecycleOwner) { mealList ->
            adapter1.setValue(mealList as ArrayList<MealX>)
            Recycler.adapter = adapter1
            AreawiseArray = mealList as ArrayList<MealX>
        }
    }

    private fun ObserveRandomMeal() {
        Homemvvm.observelivedata().observe(viewLifecycleOwner, object : Observer<Meal> {
            override fun onChanged(value: Meal) {
                Glide.with(requireContext()).load(value!!.strMealThumb).into(image)
                RandomMeal = value
            }
        })
    }
}
