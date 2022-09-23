package com.willychia.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.willychia.myapplication.Room.BigDB
import android.widget.Button
import com.willychia.myapplication.databinding.FragmentPengunjungBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

class FragmentPengunjung : Fragment() {
    val db by lazy {activity?.let { BigDB(it) }}
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences?=null
    private var binding1: FragmentPengunjungBinding?=null
    private val binding get() = binding1!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_pengunjung, container, false)
        binding1 = FragmentPengunjungBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogOut: Button = view.findViewById(R.id.btnLogOut)
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)

        val user = db?.pengunjungDAO()?.getPengunjung(sharedPreferences!!.getString(id,"")!!.toInt())?.get(0)
        binding.tvNama.setText(user?.nama)
        binding.tvEmail.setText(user?.email)
        binding.tvNoPhone.setText(user?.noTelp)
        binding.tvTglLahir.setText(user?.tglLahir)
    }
}