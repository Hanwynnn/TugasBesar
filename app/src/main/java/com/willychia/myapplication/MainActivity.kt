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

    private lateinit var inputUsername: TextInputLayout
    private  lateinit var inputPassword: TextInputLayout
    private  lateinit var mainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Ubah Title paa App Bar Aplikasi
        setTitle("User Login")

        // Hubungkan variabel dengan ciew di layoutnya
        inputUsername = findViewById(R.id.textInputLayoutUsername)
        inputPassword = findViewById(R.id.textInputLayoutPassword)
        mainLayout = findViewById(R.id.mainLayout)
        val btnReg: Button = findViewById(R.id.btnReg)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnReg.setOnClickListener (View.OnClickListener{//Mengosongkan Input
            val intent = Intent(this, RegisActivity::class.java)
            startActivity(intent)
        })

        //Aksi btnLogin
        btnLogin.setOnClickListener (View.OnClickListener {
            var checkLogin = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            //Pengecekan apakah inputan kosong
            if(username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkLogin = false
            }

            //Pengeceka  apakah inputan kosong
            if(password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }

            //Ganti Password dengan Kode kalian
            if (username == "admin" && password == "admin") checkLogin = true
            if(!checkLogin)return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        })


    }
}