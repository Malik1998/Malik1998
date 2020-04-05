package com.example.androidabbyy.db

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.example.androidabbyy.Note
import com.example.androidabbyy.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.Collections.shuffle


object NoteContract {

    const val TABLE_NAME = "notes_table"
    fun createTable(db: SQLiteDatabase) {

        Log.e("ddd", "OncreateTable Started")
        db.execSQL(
            "CREATE TABLE " + TABLE_NAME
                    + " ( "
                    + Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + Columns.TEXT + " TEXT NOT NULL,"
                    + Columns.PICTURE + " INTEGER, "
                    + Columns.DATE + " TEXT"
                    + " );"
        )
        Log.e("ddd", "Oncreate NoteContract")
        init_with_data(db)
    }

    private fun init_with_data(db: SQLiteDatabase) {
        Log.e("ddd", "init with data")
        for (i in 1..50 ) {
            var image = R.drawable.books
            var text = AppSqliteOpenHelper.lorem_ipsum

            if (i % 2 == 1) {
                image = R.drawable.kot
                text = AppSqliteOpenHelper.android
            }

            val date = "$i-09-20$i"
            var list = text.toList().toMutableList()
            shuffle(list)
            text = list.joinToString("")
            db.execSQL("Insert into ${NoteContract.TABLE_NAME} " +
                    "(${NoteContract.Columns.TEXT}, " +
                    "${NoteContract.Columns.DATE}," +
                    "${NoteContract.Columns.PICTURE}) " +
                    "values (\"$text\", \"$date\", $image);")

        }

    }

    interface Columns : BaseColumns {
        companion object {
            const val _ID = "id"
            const val TEXT = "text"
            const val PICTURE = "picture"
            const val DATE = "date"
        }
    }
}