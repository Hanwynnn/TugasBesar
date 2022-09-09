package com.willychia.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {
    //Atribut yang dipakai

    private lateinit var inputEmail: TextInputLayout
    private  lateinit var inputPassword: TextInputLayout
    private  lateinit var mainLayout: ConstraintLayout
    lateinit var mBundle: Bundle

    lateinit var vEmail: String
    lateinit var vPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Ubah Title paa App Bar Aplikasi
        setTitle("User Login")

        // Hubungkan variabel dengan ciew di layoutnya
        inputEmail = findViewById(R.id.textInputLayoutEmail)
        inputPassword = findViewById(R.id.textInputLayoutPassword)
        mainLayout = findViewById(R.id.mainLayout)
        val btnReg: Button = findViewById(R.id.btnReg)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnClear: Button = findViewById(R.id.btnClear)

        btnReg.setOnClickListener (View.OnClickListener{//Masuk ke laman registrasi
            val intent = Intent(this, RegisActivity::class.java)
            startActivity(intent)
        })

        //Aksi btnClear
        btnClear.setOnClickListener{ //Mengosongkan Input
            inputEmail.getEditText()?.setText("")
            inputPassword.getEditText()?.setText("")

            //Memunculkan Snackbar
            Snackbar.make(mainLayout, "Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        //Aksi btnLogin
        btnLogin.setOnClickListener (View.OnClickListener {
            var checkLogin = false
            getBundle()
            setText()
            val username: String = inputEmail.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            //Pengecekan apakah inputan kosong
            if(username.isEmpty()){
                inputEmail.setError("Username must be filled with text")
                checkLogin = false
            }

            //Pengeceka  apakah inputan kosong
            if(password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }

            //Ganti Password dengan Kode kalian
            getBundle()
            if (username == "admin" && password == "admin" || username == vEmail && password == vPassword) checkLogin = true

            if(!checkLogin)return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        })

    }

    fun getBundle(){
        mBundle = getIntent().getExtras()!!
        vEmail = mBundle.getString("email")!!
        vPassword = mBundle.getString("password")!!
    }

    fun setText(){
        inputEmail = findViewById(R.id.textInputLayoutEmail)
        inputEmail.getEditText()?.setText(vEmail)
        inputPassword = findViewById(R.id.textInputLayoutPassword)
        inputPassword.getEditText()?.setText(vPassword)
    }
}