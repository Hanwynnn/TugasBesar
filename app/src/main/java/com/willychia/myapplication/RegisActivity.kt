package com.willychia.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import android.content.Intent
import android.view.View

class RegisActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputNoTelp: TextInputLayout
    private lateinit var regisLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)

        setTitle("User Register")

        inputUsername = findViewById(R.id.textInputLayoutUsername)
        inputPassword = findViewById(R.id.textInputLayoutPassword)
        inputEmail = findViewById(R.id.textInputLayoutEmail)
        inputNoTelp = findViewById(R.id.textInputLayoutnoTelp)
        regisLayout = findViewById(R.id.regisActivity)
        val btnReg: Button = findViewById(R.id.btnReg)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnReg.setOnClickListener {  }

        btnLogin.setOnClickListener (View.OnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
    }
}