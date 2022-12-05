package com.willychia.TugasBesar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.willychia.TugasBesar.api.FilmApi
import com.willychia.TugasBesar.entity.Film
import es.dmoral.toasty.Toasty
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import com.willychia.TugasBesar.databinding.ActivityEditFilmBinding

class EditFilmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditFilmBinding
    private var etJudul: EditText? = null
    private var etGenre: EditText? = null
    private var etRating: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditFilmBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        queue = Volley.newRequestQueue(this)
        etJudul = findViewById(R.id.et_judul)
        etGenre = findViewById(R.id.et_genre)
        etRating = findViewById(R.id.et_rating)
        layoutLoading = findViewById(R.id.layout_loading)

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener { finish() }
//        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val id = intent.getIntExtra("id", -1)
        if(id== -1){
            tvTitle.setText("Tambah Film")
            binding.btnSave.setOnClickListener {
                binding.btnSave.startLoading()
                binding.btnSave.isLoading()
                createFilm() }
        }else{
            tvTitle.setText("Edit Film")
            getFilmById(id)

            binding.btnSave.setOnClickListener {
                binding.btnSave.startLoading()
                binding.btnSave.isLoading()
                updateMahasiswa(id) }
        }
    }

    private fun getFilmById(id: Int){
        setLoading(true)

        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET, FilmApi.GET_BY_ID_URL + id,
                { response ->
                    val jsonObject = JSONObject(response)
                    val film = Gson().fromJson(jsonObject.getString("data"), Film::class.java)

                    etJudul!!.setText(film.judul)
                    etGenre!!.setText(film.genre)
                    etRating!!.setText(film.rating.toString())
//                    Toast.makeText(this@EditFilmActivity,"Data berhasil diambil", Toast.LENGTH_SHORT).show()
                    Toasty.success(this, "Data berhasil diambil", Toast.LENGTH_SHORT, true).show()
                    setLoading(false)
                },
                Response.ErrorListener{ error ->
                    setLoading(false)
                    try{
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception){
//                        Toast.makeText(this@EditFilmActivity, e.message, Toast.LENGTH_SHORT).show()
                        Toasty.error(this, e.message.toString(), Toast.LENGTH_SHORT, true).show()
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

    private fun createFilm(){
        setLoading(true)
        val film = Film(
            etJudul!!.text.toString(),
            etGenre!!.text.toString(),
            etRating!!.text.toString().toFloat()
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.POST, FilmApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                var film = gson.fromJson(response, Film::class.java)

                if(film != null)
//                    Toast.makeText(this@EditFilmActivity, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    Toasty.success(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT, true).show()

                binding.btnSave.doResult(true)
                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
//                    Toast.makeText(this@EditFilmActivity, e.message, Toast.LENGTH_SHORT).show()
                    Toasty.error(this, e.message.toString(), Toast.LENGTH_SHORT, true).show()
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
                    val requestBody = gson.toJson(film)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        queue!!.add(stringRequest)
    }

    private fun updateMahasiswa(id: Int){
        setLoading(true)
        val mahasiswa = Film(
            etJudul!!.text.toString(),
            etGenre!!.text.toString(),
            etRating!!.text.toString().toFloat()
        )
        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, FilmApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()
                var mahasiswa = gson.fromJson(response, Film::class.java)

                if(mahasiswa != null)
//                    Toast.makeText(this@EditFilmActivity, "Data berhasil diubah", Toast.LENGTH_SHORT).show()
                    Toasty.success(this, "Data berhasil diubah", Toast.LENGTH_SHORT, true).show()

                binding.btnSave.doResult(true)
                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
//                    Toast.makeText(this@EditFilmActivity, e.message, Toast.LENGTH_SHORT).show()
                    Toasty.error(this, e.message.toString(), Toast.LENGTH_SHORT, true).show()
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
                    val requestBody = gson.toJson(mahasiswa)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }
        queue!!.add(stringRequest)
    }

    private fun setLoading(isLoading: Boolean){
        if(isLoading){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.INVISIBLE
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
}

