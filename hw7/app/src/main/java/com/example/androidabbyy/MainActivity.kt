package com.example.androidabbyy

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidabbyy.db.NoteRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Thread.sleep


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_fragment)
        super.setTitle(getString(R.string.notes))

        val job = GlobalScope.launch(context = Dispatchers.Main) {
            noteRepository = getBd()
            if (savedInstanceState == null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.list, ListNotesFragment(), ListNotesFragment.TAG)
                    .runOnCommit {
                        if (intent.hasExtra(CameraActivity.FROM_CAMERA)) {
                            showDetailFragment(intent.getLongExtra(CameraActivity.FROM_CAMERA, 1))
                        }
                    }.commit()
            } else {
                if (!resources.getBoolean(R.bool.isPhone)) {
                    supportFragmentManager.findFragmentByTag(Description.TAG)?.let {
                        supportFragmentManager.popBackStack()
                        supportFragmentManager
                            .beginTransaction()
                            .show(supportFragmentManager.findFragmentByTag(ListNotesFragment.TAG)!!)
                            .commit()
                    }
                } else {
                    supportFragmentManager.findFragmentByTag(Description.TAG)?.let {
                        supportFragmentManager
                            .beginTransaction()
                            .hide(supportFragmentManager.findFragmentByTag(ListNotesFragment.TAG)!!)
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }

        }
        findViewById<FloatingActionButton>(R.id.cameraOpener).setOnClickListener {
            var intent = Intent(this, CameraActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun showDetailFragment(id_key: Long) {
        if (supportFragmentManager.findFragmentByTag(Description.TAG) != null) { // Если на экране уже есть фрагмент с деталями, то надо его убрать перед показом нового
            supportFragmentManager.popBackStack()
        }
        val placeToDescription = R.id.details

        if (resources.getBoolean(R.bool.isPhone)) {
            supportFragmentManager.findFragmentByTag(ListNotesFragment.TAG)?.let {
                supportFragmentManager
                    .beginTransaction()
                    .hide(it)
                    .addToBackStack(null)
                    .commit()
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(placeToDescription, Description.newInstance(id_key), Description.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (resources.getBoolean(R.bool.isPhone)) {
            super.onBackPressed()
        }
        super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    suspend fun getBd() = withContext(Dispatchers.IO) {
        sleep(100)
        return@withContext App.databaseHolder?.let { NoteRepository(it) }
    }

    companion object {
        val IS_OPENED = "IS_OPENED"
        val ID = "ID"

        private var noteRepository: NoteRepository? = null

        fun getNoteRepository() : NoteRepository? {
            return noteRepository
        }
    }

}
