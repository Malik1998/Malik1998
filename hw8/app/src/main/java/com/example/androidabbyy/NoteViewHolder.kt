package com.example.androidabbyy

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.io.File
import java.text.SimpleDateFormat

class NoteViewHolder(
    itemView: View,
    listener: NoteAdapter.Listener?
) : RecyclerView.ViewHolder(itemView) {



    var imageView: ImageView? = null
    var dateTextView: TextView? = null
    var text: TextView? = null
    var id: Long = 1

    init {
        imageView = itemView.findViewById(R.id.imageView)
        dateTextView = itemView.findViewById(R.id.dateView)
        text = itemView.findViewById(R.id.textView)


        itemView.findViewById<CardView>(R.id.annotation).setOnClickListener {
            listener?.onNoteClick(id)
        }
    }

    fun bind(note: Note) {
        note.drawableRes.let {

            val picture = it.toIntOrNull()
            if (picture != null) {
                imageView?.setImageResource(picture)
            } else {
                val uri: Uri = Uri.fromFile(File(note?.drawableRes))
                Picasso
                    .get()
                    .load(uri)
                    .fit()
                    .centerInside()
                    .into(itemView.findViewById<ImageView>(R.id.imageView))
            }
        }
        dateTextView?.setText(SimpleDateFormat("yy.mm.dd").format(note.date))
        text?.setText(note.text)
        id = note.id.toLong()
    }

    fun setImageDrawable(view: ImageView, drawable: Drawable) {
        view.setImageDrawable(drawable)
    }
}