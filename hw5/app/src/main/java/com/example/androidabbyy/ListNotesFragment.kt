package com.example.androidabbyy

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListNotesFragment : Fragment(), NoteAdapter.Listener {

    val repository = MainActivity.getNoteRepository()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.activity_main, container, false)
        activity?.setTitle(R.string.notes)


        val recyclerView: RecyclerView = view.findViewById(R.id.recycle_view)

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT &&
                resources.getBoolean(R.bool.isDualSpanCount)) {
            recyclerView.layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.recycledViewPool.setMaxRecycledViews(0, 5)

        val adapter = NoteAdapter()
        recyclerView.adapter = adapter
        repository?.getNotes()?.let { adapter.setPersonList(it) }
        adapter.setListener(this)

        return view

    }

    override fun onNoteClick(id: Long) {
        (activity as MainActivity).showDetailFragment(id)
    }

    companion object {
        val TAG = "LIST_NOTES"
    }

}
