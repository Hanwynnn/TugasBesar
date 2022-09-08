package com.willychia.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
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

        btnReg.setOnClickListener (View.OnClickListener{//Mengosongkan Input
            val intent = Intent(this, RegisActivity::class.java)
            startActivity(intent)

            getBundle()
            setText()
        })

        //Aksi btnLogin
        btnLogin.setOnClickListener (View.OnClickListener {
            var checkLogin = false
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
            if (username == "admin" && password == "admin" || username == vEmail && password == vPassword) checkLogin = true

            if(!checkLogin)return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        })

    }

    fun getBundle(){
        mBundle = intent.getBundleExtra("register")!!
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