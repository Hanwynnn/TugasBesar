package com.willychia.TugasBesar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.willychia.TugasBesar.Room.BigDB


class MainActivity : AppCompatActivity() {
    //Atribut yang dipakai
    val db by lazy { BigDB(this) }

    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout
    private var checkLogin = false
    lateinit var mBundle: Bundle

    lateinit var vEmail: String
    lateinit var vPassword: String
    private val myPreference = "myPref"
    private val id = "idKey"
    var sharedPreferences: SharedPreferences?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        //Ubah Title paa App Bar Aplikasi

        // Hubungkan variabel dengan ciew di layoutnya
        inputEmail = findViewById(R.id.textInputLayoutEmail)
        inputPassword = findViewById(R.id.textInputLayoutPassword)
        mainLayout = findViewById(R.id.mainLayout)
        val btnReg: Button = findViewById(R.id.btnReg)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnClear: Button = findViewById(R.id.btnClear)

        getBundle()
        setText()

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

            val username: String = inputEmail.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            //Ganti Password dengan Kode kalian
            val user = db.pengunjungDAO().getNotesPengunjung()
            Log.d("MainActivity", "dbResponse: $user")

            for(i in user){
                if (username == i.email && password == i.password){
                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                    editor.putString(id, i.idPengunjung.toString()).apply()
                    checkLogin=true
                    break
                }
            }

            //Pengecekan apakah inputan kosong
            if(username.isEmpty()){
                inputEmail.setError("Username must be filled with text")
                checkLogin = false
            }

            //Pengeceka  apakah inputan kosong
            if(password.isEmpty()) {
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }

            if(!checkLogin){
                Snackbar.make(mainLayout, "Username Atau Password Salah", Snackbar.LENGTH_LONG).show()
                return@OnClickListener
            }else{
                val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(moveHome)
            }
        })
    }

        fun getBundle(){
            vEmail = intent.getStringExtra("email").toString()
            vPassword = intent.getStringExtra("password").toString()
        }

        fun setText(){
            if(vEmail!="null") {
                inputEmail.getEditText()?.setText(vEmail)
            }
        }
}