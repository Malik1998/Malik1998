package com.example.androidabbyy

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
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

        itemView.findViewById<ImageView>(R.id.imageView3).setOnClickListener {
            val popupMenu = PopupMenu(itemView.context, itemView.findViewById<ImageView>(R.id.imageView3))
            popupMenu.inflate(R.menu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.share -> {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, itemView.findViewById<TextView>(R.id.textView).text)
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(itemView.context, shareIntent, null)
//                    text_view.text = "Вы выбрали PopupMenu 1"
                        true
                    }
                    R.id.delete -> {
                        val dialog = AlertDialog
                            .Builder(itemView.context)
                            .setTitle(R.string.delete)
                            .setMessage(R.string.sureToDelete)
                            .setPositiveButton(R.string.yes) { dialog, which ->
                                (itemView.context as MainActivity).deleteFromBd(id)
                            }
                            .setNegativeButton(R.string.no) { dialog, which ->

                            }
                            .create()
                        dialog.show()
//                    text_view.text = "Вы выбрали PopupMenu 2"
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
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