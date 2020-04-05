package com.example.androidabbyy.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import com.example.androidabbyy.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class NoteRepository(private val databaseHolder: DatabaseHolder) {
    fun create(note: Note) {
        try {
            val database = databaseHolder.open()
            val contentValues = ContentValues()
            contentValues.put(NoteContract.Columns.TEXT, note.text)
            contentValues.put(NoteContract.Columns.DATE, note.date.toString())
            contentValues.put(NoteContract.Columns.PICTURE, note.drawableRes)
            database!!.insert(NoteContract.TABLE_NAME, null, contentValues)
        } finally {
            databaseHolder.close()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun loadAll(): List<Note> {
        val noteList : MutableList<Note> = ArrayList()
        var cursor: Cursor? = null
        try {
            val database = databaseHolder.open()
            cursor = database!!.query(
                NoteContract.TABLE_NAME,
                arrayOf(NoteContract.Columns._ID, NoteContract.Columns.TEXT, NoteContract.Columns.DATE, NoteContract.Columns.PICTURE),
                null,
                null,
                null,
                null,
                null
            )
            Log.e("ddd", "1")
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(NoteContract.Columns._ID))
                val date = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.DATE))
                val text = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.TEXT))
                val drawableRes = cursor.getInt(cursor.getColumnIndex(NoteContract.Columns.PICTURE))
                val note = Note(id, SimpleDateFormat(Note.dataType).parse(date), text, drawableRes)
                noteList.add(note)
            }
        } finally {
            cursor?.close()
            databaseHolder.close()
        }
        return noteList
    }

    @SuppressLint("SimpleDateFormat")
    suspend fun loadById(id: Long): Note = withContext(Dispatchers.IO) {
        var cursor: Cursor? = null
        try {
            val database = databaseHolder.open()
            val id_string = id.toString()
            cursor = database!!.query(
                NoteContract.TABLE_NAME,
                arrayOf(NoteContract.Columns._ID, NoteContract.Columns.TEXT, NoteContract.Columns.DATE, NoteContract.Columns.PICTURE),
                NoteContract.Columns._ID + " = ? ",
                arrayOf(id_string),
                null,
                null,
                null
            )
            cursor.moveToNext()

            val date = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.DATE))
            val text = cursor.getString(cursor.getColumnIndex(NoteContract.Columns.TEXT))
            val drawableRes = cursor.getInt(cursor.getColumnIndex(NoteContract.Columns.PICTURE))
            return@withContext Note(id.toInt(), SimpleDateFormat("dd-MM-yyyy").parse(date), text, drawableRes)
        } finally {
            cursor?.close()
            databaseHolder.close()
        }
    }

    fun getNotes(): List<Note> {
        return loadAll()
    }

    suspend fun getNoteWithId(id: Long): Note {
      //  sleep(100000)
        return loadById(id)
    }

}