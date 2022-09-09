package com.willychia.myapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import android.content.Intent
import android.view.View
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog
import java.util.*

class RegisActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputNoTelp: TextInputLayout
    private lateinit var inputTanggal: TextInputLayout
    private lateinit var btnReg: Button
    private lateinit var btnLogin: Button
    private lateinit var regisLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)

        setTitle("User Register")

        inputUsername = findViewById(R.id.textInputLayoutEmail)
        inputPassword = findViewById(R.id.textInputLayoutPassword)
        inputEmail = findViewById(R.id.textInputLayoutEmail)
        inputNoTelp = findViewById(R.id.textInputLayoutnoTelp)
        inputTanggal = findViewById(R.id.textInputLayoutTanggal)
        btnReg = findViewById(R.id.btnReg)
        btnLogin = findViewById(R.id.btnLogin)
        val cal = Calendar.getInstance()
        val tahun = cal.get(Calendar.YEAR)
        val bulan = cal.get(Calendar.MONTH)
        val hari = cal.get(Calendar.DAY_OF_MONTH)

            inputTanggal.setOnClickListener {
                val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                    inputTanggal.hint="Tanggal:" + hari + "/ " + (bulan + 1) + "/ " + tahun
                },tahun,bulan,hari)
                datePickerDialog.show()
            }


            btnReg.setOnClickListener (View.OnClickListener{
                val intent = Intent(this, MainActivity::class.java)
                val mBundle = Bundle()

                mBundle.putString("email", inputEmail.getEditText()?.toString())
                mBundle.putString("password", inputPassword.getEditText()?.toString())
                intent.putExtras(mBundle)

                startActivity(intent)
            })

            btnLogin.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@RegisActivity, MainActivity::class.java)
                startActivity(intent)
            })
        }


    }