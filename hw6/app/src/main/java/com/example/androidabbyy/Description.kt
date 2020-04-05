package com.example.androidabbyy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class Description : Fragment() {

    companion object {
        val TAG = "DESCRIPTION"

        private val ID_KEY = "ID_KEY"

        fun newInstance(id_key: Long): Fragment {
            val fragment: Fragment = Description()
            val arguments = Bundle()
            arguments.putLong(ID_KEY, id_key)
            fragment.arguments = arguments
            return fragment
        }
    }

    val repository = MainActivity.getNoteRepository()
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_description, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        job = GlobalScope.launch(Dispatchers.Main) {
            // главный поток
            var note = repository?.getNoteWithId(1) // фоновый поток
            if (arguments != null) { // главный поток
                note = repository?.getNoteWithId(arguments!!.getLong(ID_KEY, 1)) // фоновый поток
            }
            view.findViewById<TextView>(R.id.textView3).setText(note?.text) // главный поток
            note?.drawableRes?.let {
                // главный поток
                view.findViewById<ImageView>(R.id.imageView2).setImageResource(it) // главный поток
            } // главный поток
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
    }

}
