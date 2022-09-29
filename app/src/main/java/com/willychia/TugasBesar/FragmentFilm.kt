package com.willychia.TugasBesar

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.willychia.TugasBesar.Room.BigDB
import com.willychia.TugasBesar.Room.Constant
import com.willychia.TugasBesar.Room.RoomFilm.NoteFilm
import kotlinx.android.synthetic.main.fragment_film.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class FragmentFilm : Fragment() {
    val db by lazy { activity?.let { BigDB(it) } }
    var noteFilmAdapter: RVFilmAdapter ?= null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        setupListener()
//        setupRecyclerView()
//    }
aawdadaw
    //awduaidawudiawbibdiaw
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupRecyclerView()
    }
    //berfungsi untuk membuat sebuah note status pada button yang ditekan untuk CRUD yang dilaksanakan
    //ini berhubungan dengan Constant status pada room
    //cara panggil id dengan memanggil fungsi intetnEdit.
    //jika pada fungsi interface adapterListener berubah, maka object akan memerah error karena penambahan fungsi.
    private fun setupRecyclerView() {
        noteFilmAdapter = RVFilmAdapter(arrayListOf(), object :
            RVFilmAdapter.OnAdapterListener{
            override fun onClick(note: NoteFilm) {
                Toast.makeText(context, note.judul, Toast.LENGTH_SHORT).show()
                intentEdit(note.id, Constant.TYPE_READ)
            }
            override fun onUpdate(note: NoteFilm) {
                intentEdit(note.id, Constant.TYPE_UPDATE)
            }
            override fun onDelete(note:  NoteFilm) {
                deleteDialog(note)
            }
        })
        rv_film.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteFilmAdapter
        }
    }
    private fun deleteDialog(note: NoteFilm){
        activity?.let { it ->
            MaterialAlertDialogBuilder(it)
                .setTitle("Are You Sure to delete this data from ${note.judul}?")
                .setNegativeButton("Cancel", DialogInterface.OnClickListener
                { dialogInterface, i ->
                dialogInterface.dismiss()
                })
                .setPositiveButton("Delete", DialogInterface.OnClickListener
                { dialogInterface, i ->
                    dialogInterface.dismiss()
                        db?.filmDAO()?.deleteNoteFilm(note)
                        loadData()
                })
                .show()
        }
    }
    override fun onStart() {
        super.onStart()
        loadData()
    }
    //untuk load data yang tersimpan pada database yang sudah create data
    fun loadData() {
            val notes = db?.filmDAO()?.getNotesFilm()
            Log.d("FragmentFilm","dbResponse: $notes")
//            withContext(Dispatchers.Main){
                noteFilmAdapter?.setData( notes !!)
//            }
    }
    fun setupListener() {
        button_create.setOnClickListener{
            intentEdit(0,Constant.TYPE_CREATE)
        }
    }
    //pick data dari Id yang sebagai primary key
    fun intentEdit(noteId : Int, intentType: Int){
        startActivity(
            Intent(context, EditFilmActivity::class.java)
                .putExtra("intent_id", noteId)
                .putExtra("intent_type", intentType)
        )
    }
}