package com.willychia.TugasBesar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.willychia.TugasBesar.api.FilmApi
import com.willychia.TugasBesar.entity.Film
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class FragmentFilm : AppCompatActivity() {
    private var srFilm: SwipeRefreshLayout? = null
    private var adapter: RVFilmAdapter? = null
    private var svFilm: SearchView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_film)

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srFilm = findViewById(R.id.sr_film)
        svFilm = findViewById(R.id.sv_film)

        srFilm?.setOnRefreshListener (SwipeRefreshLayout.OnRefreshListener { allFilm() })
        svFilm?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter!!.filter.filter(p0)
                return false
            }
        })

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener {
            val i = Intent(this@FragmentFilm,  EditFilmActivity::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }

        val rvProduk = findViewById<RecyclerView>(R.id.rv_film)
        adapter = RVFilmAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allFilm()
    }

    private fun allFilm(){
        srFilm!!.isRefreshing = true
        val stringRequest : StringRequest = object:
            StringRequest(Method.GET, FilmApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                var film : Array<Film> = gson.fromJson(
                    jsonObject.getJSONArray("data").toString(),
                    Array<Film>::class.java
                )

                adapter!!.setFilmList(film)
                adapter!!.filter.filter(svFilm!!.query)
                srFilm!!.isRefreshing = false

                if(!film.isEmpty())
                    Toast.makeText(this@FragmentFilm, "Data berhasil diambil", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this@FragmentFilm, "Data Kosong!", Toast.LENGTH_SHORT).show()

            }, Response.ErrorListener { error ->
                srFilm!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@FragmentFilm, errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    Toast.makeText(this@FragmentFilm, e.message, Toast.LENGTH_SHORT).show()
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

    fun deleteFilm(id: Int){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, FilmApi.DELETE_URL+id, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var film = gson.fromJson(response, Film::class.java)
                if(film != null)
                    Toast.makeText(this@FragmentFilm, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()

                allFilm()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(this@FragmentFilm, errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: java.lang.Exception){
                    Toast.makeText(this@FragmentFilm, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = java.util.HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LAUNCH_ADD_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                allFilm()
            }
        }
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}