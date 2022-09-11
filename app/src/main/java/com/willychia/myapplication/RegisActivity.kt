package com.willychia.myapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import android.content.Intent
import android.view.View
import androidx.core.view.isEmpty
import com.google.android.material.snackbar.Snackbar
import java.util.*

class RegisActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputNoTelp: TextInputLayout
    private lateinit var btnTgl: Button
    private lateinit var btnReg: Button
    private lateinit var btnLogin: Button
    private lateinit var regisLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)

        setTitle("User Register")

        inputUsername = findViewById(R.id.textInputLayoutNama)
        inputPassword = findViewById(R.id.textInputLayoutPassword)
        inputEmail = findViewById(R.id.textInputLayoutEmail)
        inputNoTelp = findViewById(R.id.textInputLayoutnoTelp)
        btnTgl = findViewById(R.id.btnTgl)
        btnReg = findViewById(R.id.btnReg)
        regisLayout = findViewById(R.id.regisActivity)
        val cal = Calendar.getInstance()
        val tahun = cal.get(Calendar.YEAR)
        val bulan = cal.get(Calendar.MONTH)
        val hari = cal.get(Calendar.DAY_OF_MONTH)

            btnTgl.setOnClickListener {
                val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                    btnTgl.text="Tanggal:" + hari + "/ " + (bulan + 1) + "/ " + tahun
                },tahun,bulan,hari)
                datePickerDialog.show()
            }


            btnReg.setOnClickListener (View.OnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                val mBundle = Bundle()
                var checkReg: Boolean = false

                if(inputEmail.isEmpty() || inputPassword.isEmpty() || inputUsername.isEmpty() || inputNoTelp.isEmpty()){
                    checkReg = false
                }

                if(!checkReg){
                    Snackbar.make(regisLayout, "Data masih ada yang kosong", Snackbar.LENGTH_LONG).show()
                }else{
                    intent.putExtra("email", inputEmail.getEditText()?.text.toString())
                    intent.putExtra("password", inputPassword.getEditText()?.text.toString())

                    startActivity(intent)
                }
            })
        }


    }