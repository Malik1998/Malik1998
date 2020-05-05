package com.example.androidabbyy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(), NoteAdapter.Listener {

    val repository : NoteRepositiry = NoteRepositiry()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        super.setTitle(getString(R.string.notes))


        val recyclerView: RecyclerView = findViewById(R.id.recycle_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.recycledViewPool.setMaxRecycledViews(0, 5)

        val adapter = NoteAdapter()
        recyclerView.adapter = adapter
        adapter.setPersonList(NoteRepositiry.getNotes())
        adapter.setListener(this)
    }

    override fun onNoteClick(id: Long) {
        startActivity(Description.getIntent(this, id))
    }

}
