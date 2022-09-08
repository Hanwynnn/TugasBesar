package com.willychia.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import android.content.Intent
import android.view.View
import com.google.android.material.datepicker.MaterialDatePicker

class RegisActivity : AppCompatActivity() {
    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputNoTelp: TextInputLayout
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
        btnReg = findViewById(R.id.btnReg)
        btnLogin = findViewById(R.id.btnLogin)


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