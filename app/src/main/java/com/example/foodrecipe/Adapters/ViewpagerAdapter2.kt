package com.example.foodrecipe.Adapters

import com.example.foodrecipe.Fragments.Category
import com.example.foodrecipe.Fragments.Favourite
import com.example.foodrecipe.Fragments.Home
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager

internal class ViewpagerAdapter2(val context: Context, val FragmentManager: FragmentManager, val TotalTabs: Int): FragmentPagerAdapter(FragmentManager){
    override fun getCount(): Int {
        return TotalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0->{
                Home()
            }
            1->{
                Favourite()
            }
            2->{
                Category()
            }
            else-> getItem(position)
        }
    }
}