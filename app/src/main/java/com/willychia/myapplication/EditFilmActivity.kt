package com.willychia.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.willychia.myapplication.Room.BigDB
import com.willychia.myapplication.Room.Constant
import com.willychia.myapplication.Room.RoomFilm.NoteFilm
import kotlinx.android.synthetic.main.activity_edit_film.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Float

class EditFilmActivity : AppCompatActivity() {
    val db by lazy { BigDB(this) }
    private var noteId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()

        Toast.makeText(this, noteId.toString(), Toast.LENGTH_SHORT).show()
    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType){
            Constant.TYPE_CREATE -> {
                button_update.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                button_save.visibility = View.GONE
                button_update.visibility = View.GONE
                getNote()
            }
            Constant.TYPE_UPDATE -> {
                button_save.visibility = View.GONE
                getNote()
            }
        }
    }
    private fun setupListener() {
        button_save.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.filmDAO().addNoteFilm(
                    NoteFilm(0,edit_judul.text.toString(),
                        edit_genre.text.toString(),
                        edit_rating.text.toString().toFloat())
                )
                finish()
            }
        }
        button_update.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                NoteFilm(0,edit_judul.text.toString(),
                    edit_genre.text.toString(),
                    edit_rating.text.toString().toFloat())

                finish()
            }
        }
    }
    fun getNote() {
        noteId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val notes = db.filmDAO().getNote(noteId)[0]
            edit_judul.setText(notes.judul)
            edit_genre.setText(notes.genre)
            edit_rating.setText(notes.rating.toString())
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}