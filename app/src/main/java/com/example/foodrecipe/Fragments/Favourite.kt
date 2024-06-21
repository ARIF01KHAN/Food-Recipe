package com.example.foodrecipe.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.foodrecipe.Adapters.CategoryAdapter
import com.example.foodrecipe.Adapters.FavoriteAdapter
import com.example.foodrecipe.DataClass.FavoriteDataClass
import com.example.foodrecipe.DataClass.MealX
import com.example.foodrecipe.LikeToEatActivity
import com.example.foodrecipe.ModelView.CategoryViewModel
import com.example.foodrecipe.ModelView.SwipeGesture
import com.example.foodrecipe.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Favourite : Fragment() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var email : String
    private lateinit var recycler: RecyclerView
    private lateinit var categoryAdapter: FavoriteAdapter
    private lateinit var Adddata : ArrayList<FavoriteDataClass>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = FirebaseAuth.getInstance().currentUser?.email.toString()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recycler = view.findViewById(R.id.favorite_recyclerview)
        Adddata = arrayListOf()




        recycler.layoutManager = GridLayoutManager(requireContext(),2,LinearLayoutManager.VERTICAL,false)

        databaseReference = FirebaseDatabase.getInstance().getReference("UserID").child(email.removeSuffix("@gmail.com"))
        categoryAdapter = FavoriteAdapter(Adddata)
        recycler.adapter = categoryAdapter

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    Adddata.clear()
                    Log.d("Main","working")
                    for(data in snapshot.children){
                        val new = data.getValue(FavoriteDataClass::class.java)
                        Adddata.add(new!!)
                        Log.d("Main",new.strMeal.toString())

                    }
                    categoryAdapter.notifyDataSetChanged()


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        val swipeGesture = object : SwipeGesture(requireContext()) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val position = viewHolder.adapterPosition
                        val itemidval = Adddata[position].idMeal
                        databaseReference.child(itemidval.toString()).removeValue()
                            .addOnCompleteListener {
                                categoryAdapter.notifyDataSetChanged()

                            }
                    }

                    ItemTouchHelper.RIGHT -> {
                        val position = viewHolder.adapterPosition
                        val itemidval = Adddata[position].idMeal
                        databaseReference.child(itemidval.toString()).removeValue()
                            .addOnCompleteListener {
                                categoryAdapter.notifyDataSetChanged()
                            }
                    }
                }

            }
        }
        val itemHelper = ItemTouchHelper(swipeGesture)
        itemHelper.attachToRecyclerView(recycler)
        categoryAdapter.OnitemClicked(object : FavoriteAdapter.ClickListnerSetter {
            override fun OnClicked(position: Int) {
                if (position != RecyclerView.NO_POSITION) {
                    val curitem = Adddata[position]
                    val intent = Intent(requireContext(), LikeToEatActivity::class.java)
                    intent.putExtra("MealId", curitem.idMeal)
                    intent.putExtra("MeaLName", curitem.strMeal)
                    intent.putExtra("MealImage", curitem.strMealThumb)
                    startActivity(intent)
                }
            }
        })

        return view
    }

}