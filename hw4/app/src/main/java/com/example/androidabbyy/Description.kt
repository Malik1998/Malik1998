package com.example.androidabbyy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


class Description : Fragment() {

    companion object {
        val TAG  = "DESCRIPTION"

        private val ID_KEY = "ID_KEY"
        fun newInstance(id_key: Long): Fragment {
            val fragment: Fragment = Description()
            val arguments = Bundle()
            arguments.putLong(ID_KEY, id_key)
            fragment.arguments = arguments
            return fragment
        }
    }



    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.activity_description, container, false)
        var note = NoteRepositiry.getNoteWithId(1)
        if (arguments != null) {
            note = NoteRepositiry.getNoteWithId(arguments!!.getLong(ID_KEY, 1))
        }


        view.findViewById<TextView>(R.id.textView3).setText(note?.text)
        note?.drawableRes?.let { view.findViewById<ImageView>(R.id.imageView2).setImageResource(it) }

        return view

    }

}
