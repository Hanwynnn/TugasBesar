package com.willychia.myapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.core.view.isEmpty
import com.google.android.material.snackbar.Snackbar
import com.willychia.myapplication.Room.BigDB
import com.willychia.myapplication.Room.RoomPengunjung.NotePengunjung
import com.willychia.myapplication.databinding.ActivityRegisBinding
import java.util.*

class RegisActivity : AppCompatActivity() {
    val db by lazy { BigDB(this) }
    private var userId: Int = 0
    private lateinit var binding: ActivityRegisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityRegisBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val cal = Calendar.getInstance()
        val tahun = cal.get(Calendar.YEAR)
        val bulan = cal.get(Calendar.MONTH)
        val hari = cal.get(Calendar.DAY_OF_MONTH)


        binding.btnTgl.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, tahun, bulan, hari ->
                binding.btnTgl.text="" + hari + "/ " + (bulan.toInt() + 1).toString() + "/ " + tahun },tahun,bulan,hari)
            datePickerDialog.show()
        }



        binding.btnReg.setOnClickListener (View.OnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            val mBundle = Bundle()
            var checkReg: Boolean = false

            if(binding.textInputLayoutEmail.isEmpty() || binding.textInputLayoutPassword.isEmpty() || binding.textInputLayoutNama.isEmpty() || binding.textInputLayoutnoTelp.isEmpty()){
                checkReg = false
            }else{
                checkReg = true
            }

            if(!checkReg){
                Snackbar.make(binding.regisActivity, "Data masih ada yang kosong", Snackbar.LENGTH_LONG).show()
            }else{
                intent.putExtra("email", binding.textInputLayoutEmail.getEditText()?.text.toString())
                intent.putExtra("password", binding.textInputLayoutPassword.getEditText()?.text.toString())

                db.pengunjungDAO().addNotePengunjung(
                    NotePengunjung(0, binding.textInputLayoutNama.getEditText()?.text.toString(), binding.btnTgl.text.toString(), binding.textInputLayoutEmail.getEditText()?.text.toString()
                        , binding.textInputLayoutPassword.getEditText()?.text.toString(), binding.textInputLayoutnoTelp.getEditText()?.text.toString())
                )

                startActivity(intent)
            }


        })
    }

}