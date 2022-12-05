package com.willychia.TugasBesar

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.willychia.TugasBesar.Room.BigDB
import com.willychia.TugasBesar.databinding.ActivityEditBinding
import android.app.DatePickerDialog
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.willychia.TugasBesar.Room.RoomPengunjung.NotePengunjung
import com.willychia.TugasBesar.api.FilmApi
import com.willychia.TugasBesar.api.PengunjungApi
import com.willychia.TugasBesar.entity.Film
import com.willychia.TugasBesar.entity.Pengunjung
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*


class EditActivity : AppCompatActivity() {
    val db by lazy { BigDB(this) }
    private val userId = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences?=null
    private lateinit var binding: ActivityEditBinding
    private var queue: RequestQueue? = null
    private var userID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        queue = Volley.newRequestQueue(this)
        userID = sharedPreferences!!.getInt("id",0)
//        getNote(id)
        getUserById(userID)

        val cal = Calendar.getInstance()
        val tahun = cal.get(Calendar.YEAR)
        val bulan = cal.get(Calendar.MONTH)
        val hari = cal.get(Calendar.DAY_OF_MONTH)

        binding.btnTgl.setOnClickListener(View.OnClickListener {
            val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { datePicker, tahun, bulan, hari  ->
                binding.btnTgl.text="" + tahun +"/" + (bulan.toInt() + 1).toString() + "/" + hari },tahun,bulan,hari)
            datePickerDialog.show()
        })

        binding.btnUpdate.setOnClickListener(View.OnClickListener {
//            db.pengunjungDAO().updateNotePengunjung(NotePengunjung(id, binding.textInputLayoutNama.getEditText()?.text.toString(),
//            binding.btnTgl.text.toString(), binding.textInputLayoutEmail.getEditText()?.text.toString(),
//            binding.textInputLayoutPassword.getEditText()?.text.toString(), binding.textInputLayoutnoTelp.getEditText()?.text.toString()))
//            finish()
            updatePengunjung(userID)
            val intent=Intent(this, HomeActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

//    fun getNote(id: Int){
//        val notes = db.pengunjungDAO().getPengunjung(id).get(0)
//        binding.textInputLayoutNama.getEditText()?.setText(notes.nama)
//        binding.textInputLayoutEmail.getEditText()?.setText(notes.email)
//        binding.textInputLayoutnoTelp.getEditText()?.setText(notes.noTelp)
//        binding.textInputLayoutPassword.getEditText()?.setText(notes.password)
//        binding.btnTgl.setText(notes.tglLahir)
//    }

    private fun updatePengunjung(id: Int){
        val pengunjung = Pengunjung(
            binding.textInputLayoutNama.getEditText()?.text.toString(),
            binding.textInputLayoutEmail.getEditText()?.text.toString(),
            binding.textInputLayoutnoTelp.getEditText()?.text.toString(),
            binding.textInputLayoutPassword.getEditText()?.text.toString(),
            binding.btnTgl.text.toString()
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, PengunjungApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                var pengunjung = gson.fromJson(response, Film::class.java)

                if(pengunjung != null)
                    Toast.makeText(this@EditActivity, "Data berhasil diubah", Toast.LENGTH_SHORT).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()
            }, Response.ErrorListener { error ->
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this@EditActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(pengunjung)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
        queue!!.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RegisActivity.LAUNCH_ADD_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){

            }
        }
    }

    private fun getUserById(id: Int) {
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET, PengunjungApi.GET_BY_ID_URL + id, Response.Listener { response ->
                    val jsonObject = JSONObject(response)
                    val pengunjung = Gson().fromJson(jsonObject.getString("data"), Pengunjung::class.java)

                    binding.textInputLayoutNama.getEditText()?.setText(pengunjung.name)
                    binding.textInputLayoutEmail.getEditText()?.setText(pengunjung.email)
                    binding.textInputLayoutnoTelp.getEditText()?.setText(pengunjung.noTelp)
                    binding.textInputLayoutPassword.getEditText()?.setText(pengunjung.password)
                    binding.btnTgl.setText(pengunjung.tglLahir)
                    Toast.makeText(this,"Data berhasil diambil", Toast.LENGTH_SHORT).show()

                },
                Response.ErrorListener{ error ->
                    try{
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception){
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }
}