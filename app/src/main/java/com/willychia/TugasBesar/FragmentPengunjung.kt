package com.willychia.TugasBesar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.willychia.TugasBesar.Room.BigDB
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.willychia.TugasBesar.api.PengunjungApi
import com.willychia.TugasBesar.databinding.FragmentPengunjungBinding
import com.willychia.TugasBesar.entity.Film
import com.willychia.TugasBesar.entity.Pengunjung
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class FragmentPengunjung : Fragment() {
    val db by lazy {activity?.let { BigDB(it) }}
    private val myPreference = "myPref"
    private val id = "idKey"
    var sharedPreferences: SharedPreferences?=null
    private var binding1: FragmentPengunjungBinding?=null
    private val binding get() = binding1!!
    private var userID : Int = 0
    private var queue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding1 = FragmentPengunjungBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)

        queue = Volley.newRequestQueue(context)
        userID = sharedPreferences!!.getInt("id",0)
        getUserById(userID)
//        val user = db?.pengunjungDAO()?.getPengunjung(sharedPreferences!!.getString(id,"")!!.toInt())?.get(0)
//        binding.tvNama.setText(user?.nama)
//        binding.tvEmail.setText(user?.email)
//        binding.tvNoPhone.setText(user?.noTelp)
//        binding.tvTglLahir.setText(user?.tglLahir)

        binding.btnLogOut.setOnClickListener(View.OnClickListener{
            exit()
        })

        binding.btnEdit.setOnClickListener{
            val intent = Intent(activity, EditActivity::class.java)
            startActivity(intent)
        }

        binding.btnLokasi.setOnClickListener{
            val intent = Intent(activity, LocationActivity::class.java)
            startActivity(intent)
        }

        binding.camButton.setOnClickListener{
            val intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
        }
    }

    fun exit(){
        activity?.let { it ->
            MaterialAlertDialogBuilder(it)
                .setTitle("Apakah Anda Ingin Keluar ?")
                .setNegativeButton("No") { dialog, which ->
                }
                .setPositiveButton("yes") { dialog, which ->
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
                .show()
        }
    }

    private fun getUserById(id: Int) {
        val stringRequest: StringRequest = object :
            StringRequest(
                Method.GET, PengunjungApi.GET_BY_ID_URL + id, Response.Listener { response ->
                    val jsonObject = JSONObject(response)
                    val pengunjung = Gson().fromJson(jsonObject.getString("data"), Pengunjung::class.java)

                    binding.tvNama.setText(pengunjung?.name)
                    binding.tvEmail.setText(pengunjung?.email)
                    binding.tvNoPhone.setText(pengunjung?.noTelp)
                    binding.tvTglLahir.setText(pengunjung?.tglLahir)
                    Toast.makeText(context,"Data berhasil diambil", Toast.LENGTH_SHORT).show()

                },
                Response.ErrorListener{ error ->
                    try{
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            context,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception){
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
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