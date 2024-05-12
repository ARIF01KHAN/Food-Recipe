package com.example.foodrecipe

import com.example.foodrecipe.Adapters.ViewPagerAdapter
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var tablayout : TabLayout
    private lateinit var Viewpager : ViewPager
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tablayout = findViewById(R.id.Tablayout1)
        Viewpager = findViewById(R.id.Viewpager)

        tablayout.addTab(tablayout.newTab().setText("SignIn"))
        tablayout.addTab(tablayout.newTab().setText("SignUp"))
        tablayout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewPagerAdapter(this, supportFragmentManager , tablayout.tabCount)
        Viewpager.adapter = adapter

        Viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tablayout))
        tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Viewpager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

    }
}