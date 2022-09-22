package com.willychia.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.willychia.myapplication.Room.BigDB
import kotlinx.android.synthetic.main.fragment_film.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class FragmentFilm : Fragment() {
//    val db by lazy {BigDB(this@FragmentFilm) }
//    private var noteId: Int = 0
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_film, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val layoutManager = LinearLayoutManager(context)
//        val adapter : RVFilmAdapter = RVFilmAdapter(NoteFilmDB.listOfFilm)
//
//        val rvFilm : RecyclerView = view.findViewById(R.id.rv_film)
//
//        rvFilm.layoutManager = layoutManager
//
//        rvFilm.setHasFixedSize(true)
//
//        rvFilm.adapter = adapter
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edit)
//        setupView()
//        setupListener()
//
////        Toast.makeText(this, noteId.toString(),Toast.LENGTH_SHORT).show()
//    }
//    fun setupView(){
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        val intentType = intent.getIntExtra("intent_type", 0)
//        when (intentType){
//            Constant.TYPE_CREATE -> {
//                button_update.visibility = View.GONE
//            }
//            Constant.TYPE_READ -> {
//                button_save.visibility = View.GONE
//                button_update.visibility = View.GONE
//                getNote()
//            }
//            Constant.TYPE_UPDATE -> {
//                button_save.visibility = View.GONE
//                getNote()
//            }
//        }
//    }
//    private fun setupListener() {
//        button_save.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.noteDao().addNote(
//                    Note(0,edit_title.text.toString(),
//                        edit_note.text.toString())
//                )
//                finish()
//            }
//        }
//        button_update.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                db.noteDao().updateNote(
//                    Note(noteId, edit_title.text.toString(),
//                        edit_note.text.toString())
//                )
//                finish()
//            }
//        }
//    }
//    fun getNote() {
//        noteId = intent.getIntExtra("intent_id", 0)
//        CoroutineScope(Dispatchers.IO).launch {
//            val notes = db.noteDao().getNote(noteId)[0]
//            edit_title.setText(notes.title)
//            edit_note.setText(notes.note)
//        }
//    }
//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return super.onSupportNavigateUp()
//    }
}