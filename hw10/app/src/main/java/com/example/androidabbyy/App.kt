package com.example.androidabbyy

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.androidabbyy.db.DatabaseHolder


@SuppressLint("Registered")
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        databaseHolder = DatabaseHolder(context as App)
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        var databaseHolder: DatabaseHolder? = null
            private set

        // Способ получения контекста из любой части приложения
        fun getContext(): Context? {
            return context
        }

    }
}