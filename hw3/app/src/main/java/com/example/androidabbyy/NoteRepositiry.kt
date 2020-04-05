package com.example.androidabbyy

import java.util.*
import kotlin.collections.HashMap

class NoteRepositiry() {
    companion object {
        val NOTE_LIST = HashMap<Long, Note>()

        val lorem_ipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        val android = "Initially developed by Android Inc., which Google bought in 2005, Android was unveiled in 2007, with the first commercial Android device launched in September 2008. The current stable version is Android 10, released on September 3, 2019. The core Android source code is known as Android Open Source Project (AOSP), which is primarily licensed under the Apache License. This has allowed variants of Android to be developed on a range of other electronics, such as game consoles, digital cameras, PCs and others, each with a specialized user interface. Some well known derivatives include Android TV for televisions and Wear OS for wearables, both developed by Google."

        init {

            for (i in 1..50 ) {
                var image = R.drawable.books
                var text = lorem_ipsum

                if (i % 2 == 1) {
                    image = R.drawable.kot
                    text = android
                }


                NOTE_LIST.put(
                    i.toLong(), Note(
                        i, Date(),
                        text,
                        image
                        // Resources.getSystem().getString(R.string.lorep_ipsum), R.drawable.books
                    )
                )
            }

        }


        fun getNotes(): List<Note> {
            return NOTE_LIST.values.toList()
        }

        fun getNoteWithId(id: Long): Note? {
            return NOTE_LIST.get(id)
        }
    }
}