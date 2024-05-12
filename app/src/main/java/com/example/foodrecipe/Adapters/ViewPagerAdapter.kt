package com.example.foodrecipe.Adapters

import com.example.foodrecipe.Fragments.SignIn
import com.example.foodrecipe.Fragments.Signup
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

internal class ViewPagerAdapter(var content: Context, fm: FragmentManager, var totalTabs: Int): FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{
                SignIn()
            }
            1->{
                Signup()
            }
            else-> getItem(position)
        }
    }
    override fun getCount(): Int {

        return totalTabs
    }
}