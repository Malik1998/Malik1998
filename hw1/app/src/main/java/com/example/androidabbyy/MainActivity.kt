package com.example.androidabbyy

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        super.setTitle(getString(R.string.notes))

        findViewById<CardView>(R.id.annotation).setOnClickListener {
            var intent = Intent(this, Description::class.java)
            startActivity(intent)
        }
    }

}
