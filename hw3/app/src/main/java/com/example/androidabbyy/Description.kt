package com.example.androidabbyy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Description : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        val note = NoteRepositiry.getNoteWithId(getIntent().getLongExtra(ID_KEY, 1))

        findViewById<TextView>(R.id.textView3).setText(note?.text)
        note?.drawableRes?.let { findViewById<ImageView>(R.id.imageView2).setImageResource(it) }


    }

    companion object {
        private val ID_KEY = "ID_KEY"
        fun getIntent(context: Context, id: Long): Intent {
            val intent = Intent(context, Description::class.java)
            intent.putExtra(ID_KEY, id)
            return intent
        }
    }
}
