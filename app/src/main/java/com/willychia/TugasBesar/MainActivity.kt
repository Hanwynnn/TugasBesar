package com.willychia.TugasBesar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.willychia.TugasBesar.Room.BigDB
import com.willychia.TugasBesar.api.FilmApi
import com.willychia.TugasBesar.api.PengunjungApi
import com.willychia.TugasBesar.entity.Film
import com.willychia.TugasBesar.entity.Pengunjung
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import com.willychia.TugasBesar.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.rv_item_film.*
import java.nio.charset.StandardCharsets


class MainActivity : AppCompatActivity() {
//    val db by lazy { BigDB(this) }
    private lateinit var binding: ActivityMainBinding
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
    private var queue: RequestQueue? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        queue = Volley.newRequestQueue(this)
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        inputEmail = findViewById(R.id.textInputLayoutEmail)
        inputPassword = findViewById(R.id.textInputLayoutPassword)
        mainLayout = findViewById(R.id.mainLayout)
//        val btnReg: Button = findViewById(R.id.btnReg)
//        val btnLogin: Button = findViewById(R.id.btnLogin)
//        val btnClear: Button = findViewById(R.id.btnClear)

        getBundle()
        setText()

        binding.btnReg.setOnClickListener (View.OnClickListener{//Masuk ke laman registrasi
            val intent = Intent(this, RegisActivity::class.java)
            startActivity(intent)
        })

        //Aksi btnClear
        binding.btnClear.setOnClickListener{ //Mengosongkan Input
            inputEmail.getEditText()?.setText("")
            inputPassword.getEditText()?.setText("")

            //Memunculkan Snackbar
            Snackbar.make(mainLayout, "Text Cleared Success", Snackbar.LENGTH_LONG).show()
        }

        //Aksi btnLogin
        binding.btnLogin.setOnClickListener (View.OnClickListener {

            binding.btnLogin.startLoading()
            binding.btnLogin.isLoading()

            val username: String = inputEmail.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            checkLogin(username, password)

//            val user = db.pengunjungDAO().getNotesPengunjung()
//            Log.d("MainActivity", "dbResponse: $user")

//            for(i in user){
//                if (username == i.email && password == i.password){
//                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
//                    editor.putString(id, i.idPengunjung.toString()).apply()
//                    checkLogin=true
//                    break
//                }
//            }

            //Pengecekan apakah inputan kosong
//            if(username.isEmpty()){
//                inputEmail.setError("Username must be filled with text")
//                checkLogin = false
//            }
//
//            //Pengeceka  apakah inputan kosong
//            if(password.isEmpty()) {
//                inputPassword.setError("Password must be filled with text")
//                checkLogin = false
//            }



//            if(!checkLogin){
//                Snackbar.make(mainLayout, "Username Atau Password Salah", Snackbar.LENGTH_LONG).show()
//                return@OnClickListener
//            }else{
//                val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
//                startActivity(moveHome)
//                finish()
//            }
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

    private fun checkLogin(email: String, password: String){
        val LoginP: SharedPreferences.Editor = sharedPreferences!!.edit()

        val stringRequest : StringRequest =
            object:
            StringRequest(Method.GET, PengunjungApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var pengunjung : Array<Pengunjung> = gson.fromJson(
                    jsonObject.getJSONArray("data").toString(),
                    Array<Pengunjung>::class.java
                )

                for(i in pengunjung){
                    if(email.equals(i.email,true) && password.equals(i.password,false)){
                        binding.btnLogin.doResult(true){
                            LoginP.putInt("id", i.id!!.toInt())
                            LoginP.apply()
                            checkLogin = true
                            val intent = Intent(this, HomeActivity::class.java)
                            intent.putExtra("id", i.id)
                            startActivity(intent)
                        }
                        break
                    }
                }
                if(checkLogin == false){
//                    Toast.makeText(this, "Email or Password is incorrect", Toast.LENGTH_SHORT).show()
                    Toasty.error(this, "Email or Password is Incorrect", Toast.LENGTH_SHORT, true).show()
                    binding.btnLogin.doResult(false)
                }

            }, Response.ErrorListener { error ->
//                srFilm!!.isRefreshing = false
//                try {
//                    val responseBody =
//                        String(error.networkResponse.data, StandardCharsets.UTF_8)
//                    val errors = JSONObject(responseBody)
//                    Toast.makeText(this@FragmentFilm, errors.getString("message"), Toast.LENGTH_SHORT).show()
//                } catch (e: Exception){
                error.message?.let {
                    Toasty.error(this@MainActivity,
                        it, Toast.LENGTH_SHORT, true).show()
                }
                binding.btnLogin.doResult(false)
//                }
            }) {

//            override fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Accept"] = "application/json"
//                return headers
//            }
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["email"] = email
                params["password"] = password
                return params
            }

        }
        queue!!.add(stringRequest)
    }
}