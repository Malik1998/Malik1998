package com.example.androidabbyy.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.util.concurrent.locks.ReentrantLock


class DatabaseHolder(context: Context) {
    private val appSqliteOpenHelper: AppSqliteOpenHelper
    private var sqLiteDatabase: SQLiteDatabase? = null
    private var databaseOpenCloseBalance = 0
    private val reentrantLock: ReentrantLock = ReentrantLock()
    fun open(): SQLiteDatabase? {
        return try {
            Log.e("ddd", "open DataBaseHolder")
            reentrantLock.lock()
            if (databaseOpenCloseBalance == 0) {
                sqLiteDatabase = appSqliteOpenHelper.writableDatabase
            }
            ++databaseOpenCloseBalance
            sqLiteDatabase
        } finally {
            reentrantLock.unlock()
        }
    }

    fun close() {
        try {
            reentrantLock.lock()
            --databaseOpenCloseBalance
            if (databaseOpenCloseBalance == 0) {
                sqLiteDatabase!!.close()
                sqLiteDatabase = null
            }
        } finally {
            reentrantLock.unlock()
        }
    }

    init {
        Log.e("ddd", "init with dataddd")
        appSqliteOpenHelper = AppSqliteOpenHelper(context)
    }
}