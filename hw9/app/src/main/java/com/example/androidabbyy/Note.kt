package com.example.androidabbyy

import java.util.*


data class Note(val id: Int, val date: Date, val text: String, val drawableRes: String) {
    companion object {
        val dataType = "dd-MM-yyyy"
    }
}