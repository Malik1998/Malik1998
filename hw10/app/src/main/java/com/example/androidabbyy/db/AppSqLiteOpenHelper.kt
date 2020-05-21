package com.example.androidabbyy.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.annotation.Nullable
import com.example.androidabbyy.App
import com.example.androidabbyy.Note
import com.example.androidabbyy.R
import java.util.*
import java.util.Collections.shuffle


class AppSqliteOpenHelper(@Nullable context: Context?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        Log.e("ddd", "Oncreate AppSqlite")
        NoteContract.createTable(db)

    }


    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (oldVersion < newVersion) {
            db.execSQL("Drop Table notes_table ;")
            NoteContract.createTable(db)
        }
    }

    companion object {
        private const val DATABASE_NAME = "NotesDatabase.db"
        private const val VERSION = 36
        val lorem_ipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        val android = "Initially developed by Android Inc., which Google bought in 2005, Android was unveiled in 2007, with the first commercial Android device launched in September 2008. The current stable version is Android 10, released on September 3, 2019. The core Android source code is known as Android Open Source Project (AOSP), which is primarily licensed under the Apache License. This has allowed variants of Android to be developed on a range of other electronics, such as game consoles, digital cameras, PCs and others, each with a specialized user interface. Some well known derivatives include Android TV for televisions and Wear OS for wearables, both developed by Google."

    }
}