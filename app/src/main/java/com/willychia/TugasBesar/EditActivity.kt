package com.willychia.TugasBesar

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.willychia.TugasBesar.Room.BigDB
import com.willychia.TugasBesar.databinding.ActivityEditBinding
import android.app.DatePickerDialog
import android.content.Intent
import android.view.View
import com.willychia.TugasBesar.Room.RoomPengunjung.NotePengunjung
import java.util.*


class EditActivity : AppCompatActivity() {
    val db by lazy { BigDB(this) }
    private val userId = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences?=null
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getString(userId,"")!!.toInt()
        getNote(id)

        val cal = Calendar.getInstance()
        val tahun = cal.get(Calendar.YEAR)
        val bulan = cal.get(Calendar.MONTH)
        val hari = cal.get(Calendar.DAY_OF_MONTH)

        binding.btnTgl.setOnClickListener(View.OnClickListener {
            val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, tahun, bulan, hari  ->
                binding.btnTgl.text="" + hari + "/ " + (bulan.toInt() + 1).toString() + "/ " + tahun },tahun,bulan,hari)
            datePickerDialog.show()
        })

        binding.btnUpdate.setOnClickListener(View.OnClickListener {
            db.pengunjungDAO().updateNotePengunjung(NotePengunjung(id, binding.textInputLayoutNama.getEditText()?.text.toString(),
            binding.btnTgl.text.toString(), binding.textInputLayoutEmail.getEditText()?.text.toString(),
            binding.textInputLayoutPassword.getEditText()?.text.toString(), binding.textInputLayoutnoTelp.getEditText()?.text.toString()))
            finish()
            val intent=Intent(this, HomeActivity::class.java)
            startActivity(intent)
//            changeFragment(FragmentPengunjung())
        })
    }

    fun getNote(id: Int){
        val notes = db.pengunjungDAO().getPengunjung(id).get(0)
        binding.textInputLayoutNama.getEditText()?.setText(notes.nama)
        binding.textInputLayoutEmail.getEditText()?.setText(notes.email)
        binding.textInputLayoutnoTelp.getEditText()?.setText(notes.noTelp)
        binding.textInputLayoutPassword.getEditText()?.setText(notes.password)
        binding.btnTgl.setText(notes.tglLahir)
    }

//    fun changeFragment(fragment: Fragment){
//        getSupportFragmentManager()
//            .beginTransaction()
//            .replace(R.id.layout_fragment, fragment)
//            .commit()
//    }
}