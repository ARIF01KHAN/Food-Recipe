package com.example.foodrecipe

import com.example.foodrecipe.Adapters.ViewpagerAdapter2
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity2 : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        tabLayout = findViewById(R.id.Tablayout2)
        viewPager = findViewById(R.id.Viewpagers)

        tabLayout.addTab(tabLayout.newTab().setText("HOME").setIcon(R.drawable.baseline_home_24))
        tabLayout.addTab(tabLayout.newTab().setText("FAVORITE").setIcon(R.drawable.baseline_favorite_24))
        tabLayout.addTab(tabLayout.newTab().setText("CATEGORY").setIcon(R.drawable.baseline_category_24))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewpagerAdapter2(this, supportFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }
}