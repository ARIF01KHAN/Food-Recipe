package com.example.foodrecipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val intent = getIntent()
        val text= intent.getStringExtra("CategoryName")
        val txtview= findViewById<TextView>(R.id.txtview)
        txtview.text= text
    }
}